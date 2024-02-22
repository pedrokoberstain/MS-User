package com.compassuol.challenge3.User.unittests.mapper;

import com.compassuol.challenge3.User.data.vo.v1.UserVO;
import com.compassuol.challenge3.User.mapper.DozerMapper;
import com.compassuol.challenge3.User.model.User;
import com.compassuol.challenge3.User.unittests.mapper.mocks.MockUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DozerConverterTest {

    private MockUser mockUser;

    @BeforeEach
    public void setUp() {
        mockUser = new MockUser();
    }

    @Test
    public void testParseEntityToVO() {
        User entity = mockUser.mockEntity(0);
        UserVO vo = DozerMapper.parseObject(entity, UserVO.class);
        assertUserEquals(entity, vo);
    }

    @Test
    public void testParseEntityListToVOList() {
        List<User> entityList = mockUser.mockEntityList();
        List<UserVO> voList = DozerMapper.parseListObjects(entityList, UserVO.class);
        for (int i = 0; i < entityList.size(); i++) {
            assertEquals(entityList.get(i).getId(), voList.get(i).getId());
        }
    }

    @Test
    public void testParseVOListToEntityList() {
        List<UserVO> voList = mockUser.mockVOList();
        List<User> entityList = DozerMapper.parseListObjects(voList, User.class);
        for (int i = 0; i < voList.size(); i++) {
            assertEquals(voList.get(i).getId(), entityList.get(i).getId());
        }
    }

    private void assertUserEquals(User expected, UserVO actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getCpf(), actual.getCpf());
        assertEquals(expected.getCep(), actual.getCep());
        assertEquals(expected.getBirthdate(), actual.getBirthdate());
        assertEquals(expected.isActive(), actual.isActive());
    }

    private void assertUserVOEquals(UserVO expected, User actual) {
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
