package com.opinnapp.opinnapp.tabholder.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cayke on 09/05/17.
 */

public class OAStoryMultiChoiceImages extends OAStory {
    private List<OAImageOption> options;

    public OAStoryMultiChoiceImages () {
        options = new ArrayList<>();
        options.add(new OAImageOption());
        options.add(new OAImageOption());
        options.add(new OAImageOption());
        options.add(new OAImageOption());
    }

    public List<OAImageOption> getOptions() {
        return options;
    }
}
