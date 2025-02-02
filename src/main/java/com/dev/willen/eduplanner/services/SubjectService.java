package com.dev.willen.eduplanner.services;

import com.dev.willen.eduplanner.dto.CreateSubjectDto;
import com.dev.willen.eduplanner.entities.Subject;
import com.dev.willen.eduplanner.entities.User;
import com.dev.willen.eduplanner.repositories.SubjectRepository;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {

    private final SubjectRepository repository;
    private final UserService userService;

    public SubjectService(SubjectRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public void saveSubject(CreateSubjectDto subjectName, String userEmail) {
        User user = userService.getUserByEmail(userEmail);

        Subject subject = new Subject();
        subject.setName(subjectName.name());
        subject.setUser(user);

        repository.save(subject);
    }

}
