package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.entity.UserInfo;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.AvatarRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {

    private String avatarsDir;
    private final UserRepository userRepository;
    private final AvatarRepository avatarRepository;
    private final UserMapper userMapper;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public UserDto updateUser(UserDto userDto, String email) {
        logger.info("Was invoked method for editing user: {}", userDto);

        userDto.setEmail(email);
        UserInfo userFound = userRepository.findByEmail(email);
        userFound.setEmail(userDto.getEmail());
        userFound.setFirstName(userDto.getFirstName());
        userFound.setLastName(userDto.getLastName());
        userFound.setPhone(userDto.getPhone());
        userFound.setCity(userDto.getCity());
        userRepository.save(userFound);

        return UserMapper.INSTANCE.usertoUserDto(userFound);
    }

    @Override
    public UserDto getUser(String email) {
        logger.info("Was invoked method for getting user");
        UserInfo userFound = userRepository.findByEmail(email);
        return userMapper.usertoUserDto(userFound);
    }

    @Override
    public void updateUserImage(MultipartFile avatarFile, String email) throws IOException {
        logger.info("Was invoked method for editing user's image");
        UserInfo userInfo = userRepository.findByEmail(email);

//        Path filePath = Path.of(avatarsDir, email + "." + getExtensions(Objects.requireNonNull(avatarFile.getOriginalFilename())));
//        Files.createDirectories(filePath.getParent());
//        Files.deleteIfExists(filePath);

//        try (InputStream is = avatarFile.getInputStream();
//             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
//             BufferedInputStream bis = new BufferedInputStream(is, 1024);
//             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)) {
//            bis.transferTo(bos);
//        }
        Avatar avatar = avatarRepository.findAvatarByUserInfoId(userInfo.getId());
        if (avatar == null) {
            avatar = new Avatar();
            avatar.setFilePath("/users/" + userInfo.getId() + "/image");
            avatar.setFileSize(avatarFile.getSize());
            avatar.setMediaType(avatarFile.getContentType());
            avatar.setData(avatarFile.getBytes());
            avatar.setUserInfo(userInfo);

            avatarRepository.save(avatar);
        } else {
            avatar.setFilePath("/users/" + userInfo.getId() + "/image");
            avatar.setFileSize(avatarFile.getSize());
            avatar.setMediaType(avatarFile.getContentType());
            avatar.setData(avatarFile.getBytes());
            avatar.setUserInfo(userInfo);

            avatarRepository.save(avatar);
        }
        userInfo.setImage(avatar.getFilePath());
        userRepository.save(userInfo);
    }

    @Override
    public byte[] getImage(Long id) {
        logger.info("Was invoked method for getting avatar");
        Avatar avatar = avatarRepository.findAvatarByUserInfoId(id);
        return avatar.getData();
    }
    private String getExtensions(String fileName) {
        logger.info("Invoke method getExtensions");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
