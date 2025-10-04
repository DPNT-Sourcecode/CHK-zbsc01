package io.accelerate.solutions.HLO;

import io.accelerate.runner.SolutionNotImplementedException;

public class HelloSolution {
    public String hello(String friendName) {
        if(friendName == null || friendName.isEmpty()) {
            throw new IllegalArgumentException("Name can not be empty.");
        }

        return "Hello, " + friendName + "!";
    }
}
