package com.aldoapps.popularmovies.model;

import java.util.ArrayList;
import java.util.List;

public class AnotherResponse {

    private int page;
    private List<Result> results = new ArrayList<Result>();
    private int totalPages;
    private int totalResults;

    /**
     * No args constructor for use in serialization
     *
     */
    public AnotherResponse() {
    }

    /**
     *
     * @param results
     * @param totalResults
     * @param page
     * @param totalPages
     */
    public AnotherResponse(int page, List<Result> results, int totalPages, int totalResults) {
        this.page = page;
        this.results = results;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }

    /**
     *
     * @return
     * The page
     */
    public int getPage() {
        return page;
    }

    /**
     *
     * @param page
     * The page
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     *
     * @return
     * The results
     */
    public List<Result> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(List<Result> results) {
        this.results = results;
    }

    /**
     *
     * @return
     * The totalPages
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     *
     * @param totalPages
     * The total_pages
     */
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    /**
     *
     * @return
     * The totalResults
     */
    public int getTotalResults() {
        return totalResults;
    }

    /**
     *
     * @param totalResults
     * The total_results
     */
    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    class Result {

        private boolean adult;
        private String backdropPath;
        private List<Integer> genreIds = new ArrayList<Integer>();
        private int id;
        private String originalLanguage;
        private String originalTitle;
        private String overview;
        private String releaseDate;
        private String posterPath;
        private double popularity;
        private String title;
        private boolean video;
        private int voteAverage;
        private int voteCount;

        /**
         * No args constructor for use in serialization
         *
         */
        public Result() {
        }

        /**
         *
         * @param id
         * @param genreIds
         * @param title
         * @param releaseDate
         * @param overview
         * @param posterPath
         * @param originalTitle
         * @param voteAverage
         * @param originalLanguage
         * @param adult
         * @param backdropPath
         * @param voteCount
         * @param video
         * @param popularity
         */
        public Result(boolean adult, String backdropPath, List<Integer> genreIds, int id, String originalLanguage, String originalTitle, String overview, String releaseDate, String posterPath, double popularity, String title, boolean video, int voteAverage, int voteCount) {
            this.adult = adult;
            this.backdropPath = backdropPath;
            this.genreIds = genreIds;
            this.id = id;
            this.originalLanguage = originalLanguage;
            this.originalTitle = originalTitle;
            this.overview = overview;
            this.releaseDate = releaseDate;
            this.posterPath = posterPath;
            this.popularity = popularity;
            this.title = title;
            this.video = video;
            this.voteAverage = voteAverage;
            this.voteCount = voteCount;
        }

        /**
         *
         * @return
         * The adult
         */
        public boolean isAdult() {
            return adult;
        }

        /**
         *
         * @param adult
         * The adult
         */
        public void setAdult(boolean adult) {
            this.adult = adult;
        }

        /**
         *
         * @return
         * The backdropPath
         */
        public String getBackdropPath() {
            return backdropPath;
        }

        /**
         *
         * @param backdropPath
         * The backdrop_path
         */
        public void setBackdropPath(String backdropPath) {
            this.backdropPath = backdropPath;
        }

        /**
         *
         * @return
         * The genreIds
         */
        public List<Integer> getGenreIds() {
            return genreIds;
        }

        /**
         *
         * @param genreIds
         * The genre_ids
         */
        public void setGenreIds(List<Integer> genreIds) {
            this.genreIds = genreIds;
        }

        /**
         *
         * @return
         * The id
         */
        public int getId() {
            return id;
        }

        /**
         *
         * @param id
         * The id
         */
        public void setId(int id) {
            this.id = id;
        }

        /**
         *
         * @return
         * The originalLanguage
         */
        public String getOriginalLanguage() {
            return originalLanguage;
        }

        /**
         *
         * @param originalLanguage
         * The original_language
         */
        public void setOriginalLanguage(String originalLanguage) {
            this.originalLanguage = originalLanguage;
        }

        /**
         *
         * @return
         * The originalTitle
         */
        public String getOriginalTitle() {
            return originalTitle;
        }

        /**
         *
         * @param originalTitle
         * The original_title
         */
        public void setOriginalTitle(String originalTitle) {
            this.originalTitle = originalTitle;
        }

        /**
         *
         * @return
         * The overview
         */
        public String getOverview() {
            return overview;
        }

        /**
         *
         * @param overview
         * The overview
         */
        public void setOverview(String overview) {
            this.overview = overview;
        }

        /**
         *
         * @return
         * The releaseDate
         */
        public String getReleaseDate() {
            return releaseDate;
        }

        /**
         *
         * @param releaseDate
         * The release_date
         */
        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        /**
         *
         * @return
         * The posterPath
         */
        public String getPosterPath() {
            return posterPath;
        }

        /**
         *
         * @param posterPath
         * The poster_path
         */
        public void setPosterPath(String posterPath) {
            this.posterPath = posterPath;
        }

        /**
         *
         * @return
         * The popularity
         */
        public double getPopularity() {
            return popularity;
        }

        /**
         *
         * @param popularity
         * The popularity
         */
        public void setPopularity(double popularity) {
            this.popularity = popularity;
        }

        /**
         *
         * @return
         * The title
         */
        public String getTitle() {
            return title;
        }

        /**
         *
         * @param title
         * The title
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /**
         *
         * @return
         * The video
         */
        public boolean isVideo() {
            return video;
        }

        /**
         *
         * @param video
         * The video
         */
        public void setVideo(boolean video) {
            this.video = video;
        }

        /**
         *
         * @return
         * The voteAverage
         */
        public int getVoteAverage() {
            return voteAverage;
        }

        /**
         *
         * @param voteAverage
         * The vote_average
         */
        public void setVoteAverage(int voteAverage) {
            this.voteAverage = voteAverage;
        }

        /**
         *
         * @return
         * The voteCount
         */
        public int getVoteCount() {
            return voteCount;
        }

        /**
         *
         * @param voteCount
         * The vote_count
         */
        public void setVoteCount(int voteCount) {
            this.voteCount = voteCount;
        }

    }

}