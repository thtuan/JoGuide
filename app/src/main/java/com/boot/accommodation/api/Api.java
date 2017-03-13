package com.boot.accommodation.api;

/**
 * Created by Admin on 08/03/2016.
 */
public class Api {

//    public static class GetPlaceType extends BaseApi<PlaceTypeResponse> {
//
//        private interface Interface {
//            @GET("location/category")
//            Call<PlaceTypeResponse> getPlaceInfo();
//        }
//
//        public GetPlaceType() {
//            Interface service = RETROFIT.create(Interface.class);
//            call = service.getPlaceInfo();
//        }
//    }

//    public static class GetPlaceDetail extends BaseApi<PlaceDetailResponse> {
//
//        private interface Interface {
//            @GET("location/{placeId}")
//            Call<PlaceDetailResponse> getPlaceInfo(
//                    @Path("placeId") String id,
//                    @Header("token") String token
//            );
//        }
//
//        public GetPlaceDetail(String placeId) {
//            Interface service = RETROFIT.create(Interface.class);
//            call = service.getPlaceInfo(placeId, PreferenceUtils.getString(Constants.Preference.PREFERENCE_USER_TOKEN, ""));
//        }
//    }

//    public static class LoginAccount extends BaseApi<LoginResponse> {
//
//        private interface Interface {
//            @FormUrlEncoded
//            @POST("api/user/login")
//            Call<LoginResponse> getPlaceInfo(
//                    @Field("username") String username,
//                    @Field("password") String password
//            );
//        }
//
//        public LoginAccount(String username, String password) {
//            Interface service = RETROFIT.create(Interface.class);
//            call = service.getPlaceInfo(username, password);
//        }
//    }

//    public static void SendOrderToKitchen(Context context, String sessionId, String restaurantId,
//                                          String orderId, List<FoodOrderItem> listOrder, FutureCallback<JsonObject> requestAPI) {
//        String url = getHostUrl() + "restaurant/sendOrderItemToKitchen";
//        Builders.Any.M request = Ion.with(context).load(url)
//                .setMultipartParameter("sessionId", sessionId)
//                .setMultipartParameter("restaurantId", restaurantId)
//                .setMultipartParameter("orderId", orderId);
//
//        for (FoodOrderItem item : listOrder) {
//            request.setMultipartParameter("menuItemIds[]", item.id);
//        }
//
//        for (FoodOrderItem item : listOrder) {
//            if (TextUtils.isEmpty(item.comment)) {
//                request.setMultipartParameter("comments[]", "N/A");
//            } else {
//                request.setMultipartParameter("comments[]", item.comment);
//            }
//        }
//
//        for (FoodOrderItem item : listOrder) {
//            if (item.hasExtraOptions && !TextUtils.isEmpty(item.totalIdExtra)) {
//                request.setMultipartParameter("options[]", item.totalIdExtra);
//            } else {
//                request.setMultipartParameter("options[]", "N/A");
//            }
//        }
//
//        request.asJsonObject().setCallback(requestAPI);
//    }
}
