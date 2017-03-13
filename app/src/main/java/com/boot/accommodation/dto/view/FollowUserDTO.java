package com.boot.accommodation.dto.view;

import java.util.ArrayList;

/**
 * DTO cho man hinh ds view
 *
 * @author vuong
 * @since: 18:31 AM 5/13/2016
 */
public class FollowUserDTO extends BaseDTO {
    private PagingDTO paging;// paging
    private ArrayList<FollowItemDTO> follower;// list follower of user

    public PagingDTO getPaging() {
        return paging;
    }
    public void setPaging( PagingDTO pagingDTO) {
        this.paging = pagingDTO;
    }

    public ArrayList<FollowItemDTO> getFollower() {
        return follower;
    }

    public void setFollower( ArrayList<FollowItemDTO> follower ) {
        this.follower = follower;
    }
}
