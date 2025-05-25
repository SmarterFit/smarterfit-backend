package com.smarterfit.modules.ai.tools;

import com.smarterfit.modules.ai.tools.classes.ClassPlansTools;
import com.smarterfit.modules.ai.tools.classes.ClassSessionTools;
import com.smarterfit.modules.ai.tools.classes.ClassTools;
import com.smarterfit.modules.ai.tools.classes.UserClassTools;
import com.smarterfit.modules.ai.tools.billing.PlanTools;
import com.smarterfit.modules.ai.tools.user.ProfileMetricTools;
import com.smarterfit.modules.ai.tools.user.ProfileTools;
import com.smarterfit.modules.ai.tools.user.UserTools;
import org.springframework.stereotype.Component;

@Component
public class ToolsFaced {

    private final PlanTools planTools;
    private final ClassTools classTools;
    private final UserClassTools userClassTools;
    private final ClassSessionTools classSessionTools;
    private final ClassPlansTools classPlansTools;
    private final UserTools userTools;
    private final ProfileTools profileTools;
    private final ProfileMetricTools profileMetricTools;

    public ToolsFaced(PlanTools planTools, ClassTools classTools, UserClassTools userClassTools,
                      ClassSessionTools classSessionTools, ClassPlansTools classPlansTools, UserTools userTools,
                      ProfileTools profileTools, ProfileMetricTools profileMetricTools) {
        this.planTools = planTools;
        this.classTools = classTools;
        this.userClassTools = userClassTools;
        this.classPlansTools = classPlansTools;
        this.classSessionTools = classSessionTools;
        this.userTools = userTools;
        this.profileTools = profileTools;
        this.profileMetricTools = profileMetricTools;
    }
}
