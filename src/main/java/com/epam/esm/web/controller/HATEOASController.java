package com.epam.esm.web.controller;

import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.UserDto;
import org.springframework.hateoas.PagedModel;

import java.util.Collection;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public abstract class HATEOASController<T> {
    protected Collection<T> addPagination(List<T> entities, int page, int size, long countOfEntities) {
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(size, page, countOfEntities);
        return PagedModel.of(entities, pageMetadata).getContent();
    }

    public static GiftCertificateDto addLinksToCertificate(GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class).update(giftCertificateDto.getId(), giftCertificateDto)).withRel("update"));
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class).delete(giftCertificateDto.getId())).withRel("delete"));
        addLinksToListTag(giftCertificateDto.getTags());
        return giftCertificateDto;
    }

    public static TagDto addLinksToTag(TagDto tagDto) {
        tagDto.add(linkTo(methodOn(TagController.class).delete(tagDto.getId())).withRel("delete"));
        tagDto.add(linkTo(methodOn(TagController.class).create(tagDto)).withRel("create"));
        return tagDto;
    }

    public static UserDto addLinksToUser(UserDto user) {
        user.add(linkTo(methodOn(UserController.class).readUserOrdersByUserId(user.getId())).withRel("orders"));
        return user;
    }

    public static OrderDto addLinksToOrder(OrderDto orderDto) {
        addLinksToListCertificate(orderDto.getGiftCertificateList());
        UserDto user = orderDto.getUser();
        user.add(linkTo(methodOn(UserController.class).read(user.getId())).withRel("user"));
        return orderDto;
    }

    public static void addLinksToListCertificate(List<GiftCertificateDto> certificateDtoList) {
        for (GiftCertificateDto giftCertificateDto : certificateDtoList) {
            giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class).read(giftCertificateDto.getId())).withSelfRel());
            addLinksToListTag(giftCertificateDto.getTags());
        }
    }

    public static void addLinksToListTag(List<TagDto> tagDtoList) {
        for (TagDto tagDto : tagDtoList) {
            tagDto.add(linkTo(methodOn(TagController.class).read(tagDto.getId())).withSelfRel());
        }
    }

    public static void addLinksToListUser(List<UserDto> userDtoList) {
        for (UserDto userDto : userDtoList) {
            userDto.add(linkTo(methodOn(UserController.class).read(userDto.getId())).withSelfRel());
        }
    }

    public static void addLinksToListOrder(List<OrderDto> orderDtoList) {
        for (OrderDto orderDto : orderDtoList) {
            orderDto.add(linkTo(methodOn(UserController.class).readUserOrdersByUserId(orderDto.getUser().getId())).withRel("orders"));
            addLinksToListCertificate(orderDto.getGiftCertificateList());

            UserDto user = orderDto.getUser();
            user.add(linkTo(methodOn(UserController.class).read(user.getId())).withRel("user"));
        }
    }

    public static GiftCertificateDto addLinkToCertificate(GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class).read(giftCertificateDto.getId())).withSelfRel());
        return giftCertificateDto;
    }


}