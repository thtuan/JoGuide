package com.boot.accommodation.dto.view;

import java.util.ArrayList;

/**
 * Dto listtour
 *
 * @author Vuong-bv
 * @since: 20/6/2016
 */
public class FeedbackDTO extends BaseDTO{
    private PagingDTO paging;// paging of feedback
    private ArrayList<FeedbackItemDTO> feeback;

    public ArrayList<FeedbackItemDTO> getFeeback() {
        return feeback;
    }

    public void setFeeback( ArrayList<FeedbackItemDTO> feeback ) {
        this.feeback = feeback;
    }

    public PagingDTO getPaging() {
        return paging;
    }

    public void setPaging( PagingDTO paging ) {
        this.paging = paging;
    }
}
