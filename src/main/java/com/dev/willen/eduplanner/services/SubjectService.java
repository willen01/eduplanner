package com.dev.willen.eduplanner.services;

import com.dev.willen.eduplanner.dto.CreateSubjectDto;
import com.dev.willen.eduplanner.entities.Subject;
import com.dev.willen.eduplanner.entities.User;
import com.dev.willen.eduplanner.repositories.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    public void removeSubject(int subjectId, String userEmail) {
        User user = userService.getUserByEmail(userEmail);
        Subject subject = getSubjectById(subjectId);

        if (user.getId() != subject.getUser().getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have permission to perform this operation");
        }

        repository.deleteById(subject.getId());
    }

    public Subject getSubjectById(int subjectId) {
        return repository.findById(subjectId)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("Subject with id: " + subjectId + " not found!");
                });
    }

    public boolean existsSubjectByUser(Integer subjectId, Integer userId) {
        return repository.existsByIdAndUserId(subjectId, userId);
    }

    public List<Subject> getAllSubjects(String userEmail) {
        User user = userService.getUserByEmail(userEmail);
        return repository.findAllByUserId(user.getId());
    }
}
