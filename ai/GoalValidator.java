package ai;

import models.ConnectableNode;

public interface GoalValidator {
    boolean isValidGoal(ConnectableNode node);
}
