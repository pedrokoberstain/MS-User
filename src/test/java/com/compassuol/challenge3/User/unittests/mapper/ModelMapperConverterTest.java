package com.compassuol.challenge3.User.unittests.mapper;

import com.compassuol.challenge3.User.config.ModelMapperConfig;
import com.compassuol.challenge3.User.model.User;
import com.compassuol.challenge3.User.unittests.mapper.mocks.MockUser;
import com.compassuol.challenge3.User.web.dto.UserCreateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = {ModelMapperConfig.class})
public class ModelMapperConverterTest {

    @Autowired
    private ModelMapper modelMapper;

    private MockUser mockUser;

    @BeforeEach
    public void setUp() {
        mockUser = new MockUser();
    }

    @Test
    public void testParseEntityToVO() {
        User entity = mockUser.mockEntity(0);
        UserCreateDTO vo = modelMapper.map(entity, UserCreateDTO.class);
        assertUserEquals(entity, vo);
    }

    @Test
    public void testParseEntityListToVOList() {
        List<User> entityList = mockUser.mockEntityList();
        List<UserCreateDTO> voList = entityList.stream()
                .map(entity -> modelMapper.map(entity, UserCreateDTO.class))
                .collect(Collectors.toList());
        for (int i = 0; i < entityList.size(); i++) {
            assertEquals(entityList.get(i).getId(), voList.get(i).getId());
        }
    }

    @Test
    public void testParseVOListToEntityList() {
        List<UserCreateDTO> voList = mockUser.mockVOList();
        List<User> entityList = voList.stream()
                .map(vo -> modelMapper.map(vo, User.class))
                .collect(Collectors.toList());
        for (int i = 0; i < voList.size(); i++) {
            assertEquals(voList.get(i).getId(), entityList.get(i).getId());
        }
    }

    private void assertUserEquals(User expected, UserCreateDTO actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getCpf(), actual.getCpf());
        assertEquals(expected.getCep(), actual.getCep());
        assertEquals(expected.getBirthdate(), actual.getBirthdate());
        assertEquals(expected.isActive(), actual.isActive());
    }

    private void assertUserVOEquals(UserCreateDTO expected, User actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getCpf(), actual.getCpf());
        assertEquals(expected.getCep(), actual.getCep());
        assertEquals(expected.getBirthdate(), actual.getBirthdate());
        assertEquals(expected.isActive(), actual.isActive());
    }
}
