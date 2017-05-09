package com.opinnapp.opinnapp.tabholder.Models;

import java.util.Date;
import java.util.List;

/**
 * Created by cayke on 09/05/17.
 */

//Classe que representa a "duvida" / "pergunta"
public class OAStory {
    private OAUser owner;
    private Date creationDate;
    private Date expirationDate;
    private String description;
    private String tags;
    private List<OAComment> comments;
    private boolean favorited;
}
