package com.drunar.coincorner.listener;

import com.drunar.coincorner.database.entity.Revision;
import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        Revision exampleRevEntity = (Revision) revisionEntity;
        exampleRevEntity.setAuthor(getAuthor());
    }

    public static String getAuthor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails userDetails) {
                return userDetails.getUsername();
            }
        }
        return "himself";
    }

}
