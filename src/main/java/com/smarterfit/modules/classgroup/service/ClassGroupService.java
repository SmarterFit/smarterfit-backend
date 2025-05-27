package com.smarterfit.modules.classgroup.service;

import com.smarterfit.modules.classgroup.dto.request.classgroup.ClassGroupRequestDTO;
import com.smarterfit.modules.classgroup.dto.request.classgroup.SearchClassGroupRequestDTO;
import com.smarterfit.modules.classgroup.dto.response.ClassGroupResponseDTO;
import com.smarterfit.modules.classgroup.entity.ClassGroup;
import com.smarterfit.modules.classgroup.entity.Modality;
import com.smarterfit.modules.classgroup.event.ClassGroupDeactivatedEvent;
import com.smarterfit.modules.classgroup.mapper.ClassGroupMapper;
import com.smarterfit.modules.classgroup.repository.ClassGroupRepository;
import com.smarterfit.modules.classgroup.specification.ClassSpecifications;
import com.smarterfit.modules.classgroup.validation.ValidationFaced;
import com.smarterfit.modules.useraccess.entity.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ClassGroupService {

    private final ClassGroupRepository classGroupRepository;
    private final ValidationFaced validationFaced;
    private final ApplicationEventPublisher publisher;

    public ClassGroupService(ClassGroupRepository classGroupRepository,
            ValidationFaced validationFaced,
            ApplicationEventPublisher publisher) {

        this.classGroupRepository = classGroupRepository;
        this.validationFaced = validationFaced;
        this.publisher = publisher;

    }

    @Transactional
    public ClassGroupResponseDTO createClassGroup(ClassGroupRequestDTO requestDTO, UUID requesterId) {
        User creatorUser = validationFaced.userValidation.validateUserById(requesterId);
        validationFaced.classGroupValidation.validateClassGroupDates(requestDTO.getStartDate(),
                requestDTO.getEndDate());

        validationFaced.classGroupValidation.validateClassGroupExists(requesterId);
        Modality modality = validationFaced.modalityValidation.validateModalityById(requestDTO.getModalityId());

        ClassGroup classGroup = ClassGroupMapper.toEntity(requestDTO, modality, creatorUser);
        classGroup.setSlug(validationFaced.classGroupValidation.generateUniqueSlug(classGroup.getTitle()));
        classGroupRepository.save(classGroup);

        return ClassGroupMapper.toResponse(classGroup, creatorUser.getProfile().getFullName());
    }

    @Transactional(readOnly = true)
    public ClassGroupResponseDTO getClassGroupById(UUID id) {
        ClassGroup classGroup = validationFaced.classGroupValidation.validateClassGroupById(id);
        return ClassGroupMapper.toResponse(classGroup, classGroup.getCreatedByUser().getProfile().getFullName());
    }

    @Transactional(readOnly = true)
    // TODO: incluir filtros por: modalidade, tipo, data, disponibilidade
    public List<ClassGroupResponseDTO> getAllClassGroups() {
        List<ClassGroup> classGroups = classGroupRepository.findAllByIsActiveTrue();
        return classGroups.stream()
                .map(classGroup -> ClassGroupMapper.toResponse(classGroup,
                        classGroup.getCreatedByUser().getProfile().getFullName()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ClassGroupResponseDTO> getAvailableClassGroupsByUserId(UUID userId) {
        List<ClassGroup> classes = classGroupRepository.findAvailableClassGroupsByUser(userId);

        return classes.stream().map(ClassGroupMapper::toResponse).toList();
    }

    @Transactional
    public ClassGroupResponseDTO updateClassGroupById(UUID classGroupId, ClassGroupRequestDTO requestDTO) {
        validationFaced.classGroupValidation.validateClassGroupDates(requestDTO.getStartDate(),
                requestDTO.getEndDate());
        ClassGroup classGroup = validationFaced.classGroupValidation.validateClassGroupById(classGroupId);

        Modality modality = validationFaced.modalityValidation.validateModalityById(requestDTO.getModalityId());

        classGroup = ClassGroupMapper.toEntity(requestDTO, modality, classGroup.getCreatedByUser(), classGroup);
        classGroup.setSlug(validationFaced.classGroupValidation.generateUniqueSlug(classGroup.getTitle()));
        classGroupRepository.save(classGroup);

        return ClassGroupMapper.toResponse(classGroup, classGroup.getCreatedByUser().getProfile().getFullName());
    }

    @Transactional
    public void deleteClassGroupById(UUID id) {
        ClassGroup classGroup = validationFaced.classGroupValidation.validateClassGroupById(id);
        classGroup.setActive(false);
        publisher.publishEvent(new ClassGroupDeactivatedEvent(classGroup));
        classGroupRepository.save(classGroup);
    }

    @Transactional(readOnly = true)
    public Page<ClassGroupResponseDTO> searchClass(SearchClassGroupRequestDTO searchDTO, Pageable pageable) {
        Specification<ClassGroup> specification = ClassSpecifications.searchByFilters(searchDTO);

        Page<ClassGroup> classGroups = classGroupRepository.findAll(specification, pageable);

        return classGroups.map(ClassGroupMapper::toResponse);
    }

}
