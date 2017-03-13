package com.boot.accommodation.vp.tour;

import android.util.Log;

import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.response.CommonResponseDTO;
import com.boot.accommodation.dto.response.TourInviteResponseDTO;
import com.boot.accommodation.dto.response.TourPlanResponseDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.TourModel;
import com.boot.accommodation.model.mgr.TourModelMgr;
import com.boot.accommodation.util.TraceExceptionUtils;
import com.boot.accommodation.util.Utils;

import java.util.List;

/**
 * tour preseter
 *
 * @author tuanlt
 * @since: 10:30 PM 5/2/2016
 */
public class TourPresenter implements TourPresenterMgr {

    private TourModelMgr tourModelMgr;// tour model
    private TourViewMgr tourViewMgr;//view of tour
    private String TAG = ""; // Tag

    public TourPresenter(TourViewMgr tourViewMgr) {
        this.tourViewMgr = tourViewMgr;
        tourModelMgr = new TourModel();
        TAG = Utils.getTag(tourViewMgr.getClass());
    }

    @Override
    public void requestInvite(long tourId, long tourPlanId, List<String> list) {
        tourModelMgr.requestInvite(tourId, tourPlanId,list, new ModelCallBack<CommonResponseDTO>() {
            @Override
            public void onSuccess(CommonResponseDTO response) {
                tourViewMgr.inviteTourSuccess();
            }

            @Override
            public void onError( int errorCode, String error ) {
                tourViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    @Override
    public void getTourPlan(long tourId, long tourPlanId) {
        tourModelMgr.getTourPlan(tourId, tourPlanId, new ModelCallBack<TourPlanResponseDTO>(TAG) {
            @Override
            public void onSuccess(TourPlanResponseDTO response) {
                tourViewMgr.getTourPlanSuccess(response.getData());
            }

            @Override
            public void onError(int errorCode, String error) {
                tourViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    @Override
    public void getIInvited(long tourId, long tourPlanId) {
        tourModelMgr.getIInvited(tourId, tourPlanId, new ModelCallBack<TourInviteResponseDTO>(TAG) {
            @Override
            public void onSuccess(TourInviteResponseDTO response) {
                tourViewMgr.getIInvitedSuccess(response.getData());
            }

            @Override
            public void onError(int errorCode, String error) {
                tourViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    @Override
    public void requestAskToJoinTour(long tourId, long tourPlanId) {
        tourModelMgr.requestAskJoinTour(tourId, tourPlanId, new ModelCallBack<CommonResponseDTO>(TAG) {
            @Override
            public void onSuccess(CommonResponseDTO response) {
                try {
                    Integer result = 0;
                    if (response.getData() != null ) {
                        Double data = (Double)response.getData();
                        result = data.intValue();
                    }
                    tourViewMgr.requestAskJoinTourSuccess(result);
                }catch (Exception ex) {
                    Log.e(Constants.LogName.EXCEPTION, TraceExceptionUtils.getReportFromThrowable(ex));
                }

            }

            @Override
            public void onError(int errorCode, String error) {
                tourViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    @Override
    public void requestAcceptJoinTour(long tourId, long tourPlanId, String inviteToken) {
        tourModelMgr.requestAcceptJoinTour(tourId, tourPlanId, inviteToken, new ModelCallBack<CommonResponseDTO>(TAG) {
            @Override
            public void onSuccess(CommonResponseDTO response) {
                tourViewMgr.requestAcceptJoinTourSuccess();
            }

            @Override
            public void onError(int errorCode, String error) {
                tourViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    @Override
    public void requestCheckAskedToJoinTour(long tourId, long tourPlanId) {
        tourModelMgr.requestCheckAskedJoinTour(tourId, tourPlanId, new ModelCallBack<CommonResponseDTO>(TAG) {
            @Override
            public void onSuccess(CommonResponseDTO  response) {
                try {
                    Integer result = 0;
                    if (response.getData() != null ) {
                        Double data = (Double)response.getData();
                        result = data.intValue();
                    }
                    tourViewMgr.requestCheckAskedJoinTourSuccess(result);
                }catch (Exception ex) {
                    Log.e(Constants.LogName.EXCEPTION, TraceExceptionUtils.getReportFromThrowable(ex));
                }
            }

            @Override
            public void onError(int errorCode, String error) {

            }

        });
    }

    @Override
    public void requestDeclineJoinTour(long tourId, long tourPlanId, String inviteToken) {
        tourModelMgr.requestDeclineJoinTour(tourId, tourPlanId, inviteToken, new ModelCallBack<CommonResponseDTO>(TAG) {
            @Override
            public void onSuccess(CommonResponseDTO response) {
                tourViewMgr.requestDeclineJoinTourSuccess();
            }

            @Override
            public void onError(int errorCode, String error) {
                tourViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

}
