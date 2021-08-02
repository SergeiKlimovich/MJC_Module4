package com.epam.esm.persistence.util;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@NoArgsConstructor
@Data
public class GiftCertificateQueryParameter {
    private String name;
    private String description;
    private String fullSearch;
    private List<String> tagNamesList;

    private SortType sortType;
    private OrderType orderType;

    public GiftCertificateQueryParameter(String name, String description, String fullSearch, List<String> tagNamesList, String sortType, String orderType) {
        if (!StringUtils.isEmpty(name)) {
            setName(name);
        }
        if (!StringUtils.isEmpty(description)) {
            setDescription(description);
        }
        if (!StringUtils.isEmpty(fullSearch)) {
            setFullSearch(fullSearch);
        }

        if (tagNamesList != null && !tagNamesList.isEmpty()) {
            this.tagNamesList = tagNamesList;
        }
        if (!StringUtils.isEmpty(sortType)) {
            setSortType(sortType.toUpperCase());
        }
        if (!StringUtils.isEmpty(orderType)) {
            setOrderType(orderType.toUpperCase());
        }
    }

    public SortType getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = SortType.valueOf(sortType.toUpperCase());
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = OrderType.valueOf(orderType.toUpperCase());
    }

    public boolean isEmpty() {
        return name == null && fullSearch == null && description == null && tagNamesList == null && sortType == null && orderType == null;
    }

}
