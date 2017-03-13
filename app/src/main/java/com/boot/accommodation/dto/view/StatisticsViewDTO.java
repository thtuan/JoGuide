package com.boot.accommodation.dto.view;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * DTO view statistic
 *
 * @author thtuan
 * @since 2:30 PM 17-06-2016
 */
public class StatisticsViewDTO implements Parcelable {
    private int numTour;
    private int numFeedback;
    private List<StatisticsDTO> statistics;

    public StatisticsViewDTO()
    {}
    public StatisticsViewDTO(int numberTour, int numberSuggestion, List<StatisticsDTO> statisticsDTOList) {
        this.numTour = numberTour;
        this.numFeedback = numberSuggestion;
        this.statistics = statisticsDTOList;
    }


    public int getNumTour() {
        return numTour;
    }

    public void setNumTour(int numTour) {
        this.numTour = numTour;
    }

    public int getNumFeedback() {
        return numFeedback;
    }

    public void setNumFeedback(int numFeedback) {
        this.numFeedback = numFeedback;
    }

    public List<StatisticsDTO> getStatistics() {
        return statistics;
    }

    public void setStatistics( List<StatisticsDTO> statistics ) {
        this.statistics = statistics;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.numTour);
        dest.writeInt(this.numFeedback);
        dest.writeTypedList(this.statistics);
    }

    protected StatisticsViewDTO(Parcel in) {
        this.numTour = in.readInt();
        this.numFeedback = in.readInt();
        this.statistics = in.createTypedArrayList(StatisticsDTO.CREATOR);
    }

    public static final Creator<StatisticsViewDTO> CREATOR = new Creator<StatisticsViewDTO>() {
        @Override
        public StatisticsViewDTO createFromParcel(Parcel source) {
            return new StatisticsViewDTO(source);
        }

        @Override
        public StatisticsViewDTO[] newArray(int size) {
            return new StatisticsViewDTO[size];
        }
    };
}
