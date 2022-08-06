package com.ganipohan.mymovieapp.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ganipohan.mymovieapp.AppExecutors;
import com.ganipohan.mymovieapp.models.MovieModel;
import com.ganipohan.mymovieapp.response.MovieSearchResponse;
import com.ganipohan.mymovieapp.utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    //live data for search
    private MutableLiveData<List<MovieModel>> mMovies;
    private static MovieApiClient instance;
    //making Global runnable
    private RetrieveMoviesRunnable retrieveMoviesRunnable;

    //live data for popular movie
    private MutableLiveData<List<MovieModel>> mMoviesPop;
    //making global pupular runnabel
    private RetrieveMoviesRunnablePop retrieveMoviesRunnablePop;


    public static MovieApiClient getInstance(){
        if(instance == null){
            instance = new MovieApiClient();
        }
        return instance;
    }

    private MovieApiClient(){
        mMovies = new MutableLiveData<>();
        mMoviesPop = new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return mMovies;
    }

    public LiveData<List<MovieModel>> getMoviesPop(){
        return mMoviesPop;
    }

    //1-this method that we are going to call throught the clases
    public void searchMoviesApi(String query, int pageNumber){

        if (retrieveMoviesRunnable != null){
            retrieveMoviesRunnable = null;
        }

        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call
                myHandler.cancel(true);
            }
        }, 3000, TimeUnit.MILLISECONDS);
    }

    public void searchMoviesPop(int pageNumber){

        if (retrieveMoviesRunnablePop != null){
            retrieveMoviesRunnablePop = null;
        }

        retrieveMoviesRunnablePop = new RetrieveMoviesRunnablePop(pageNumber);

        final Future myHandler2 = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnablePop);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call
                myHandler2.cancel(true);
            }
        }, 3000, TimeUnit.MILLISECONDS);
    }


    //Retrieving data from RestAPI by runnable class
    private class RetrieveMoviesRunnable implements Runnable{

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = getMovies(query, pageNumber).execute();
                if (cancelRequest){
                    return;
                }
                if (response.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    if (pageNumber == 1){
                        //Sending data to livedata
                        //PostValue: used for background thread
                        //setValue: not for background thread
                        mMovies.postValue(list);
                    }else{
                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        //add currentMovie to list liveData
                        mMovies.postValue(currentMovies);
                    }
                }else{
                    String error = response.errorBody().string();
                    Log.v("Tag","Error "+error);
                    mMovies.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }
        }


        //Search method/query
        private Call<MovieSearchResponse> getMovies(String query, int pageNumber){
            return Servicey.getMovieApi().searchMovie(
                    Credentials.API_KEY,
                    query,
                    pageNumber
            );
        }

        private void cancelRequest(){
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }


    }

    private class RetrieveMoviesRunnablePop implements Runnable{

        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnablePop(int pageNumber) {
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response2 = getPop(pageNumber).execute();
                if (cancelRequest){
                    return;
                }
                if (response2.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response2.body()).getMovies());
                    if (pageNumber == 1){
                        //Sending data to livedata
                        //PostValue: used for background thread
                        //setValue: not for background thread
                        mMoviesPop.postValue(list);
                    }else{
                        List<MovieModel> currentMovies = mMoviesPop.getValue();
                        currentMovies.addAll(list);
                    }
                }else{
                    String error = response2.errorBody().string();
                    Log.v("Tag","Error "+error);
                    mMoviesPop.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mMoviesPop.postValue(null);
            }
        }


        //Search method/query
        private Call<MovieSearchResponse> getPop(int pageNumber){
            return Servicey.getMovieApi().getPopular(
                    Credentials.API_KEY,
                    pageNumber
            );
        }

        private void cancelRequest(){
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }


    }
}
