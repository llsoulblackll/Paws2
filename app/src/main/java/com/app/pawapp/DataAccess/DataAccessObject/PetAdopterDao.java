package com.app.pawapp.DataAccess.DataAccessObject;

import android.content.Context;

import com.app.pawapp.DataAccess.DataTransferObject.PetAdopterDto;
import com.app.pawapp.DataAccess.Entity.PetAdopter;
import com.app.pawapp.Util.Gson.GsonFactory;
import com.app.pawapp.Util.Util;
import com.app.pawapp.Util.Util.HttpHelper;
import com.google.gson.Gson;

import java.util.List;
import java.util.Locale;

public class PetAdopterDao implements Ws<PetAdopter> {

    private static final String ENDPOINT = "PetAdopterService.svc";

    private static final String INSERT_METHOD = "New";
    private static final String UPDATE_METHOD = "Update";
    private static final String FIND_METHOD = "Find";
    private static final String FIND_ALL_REQUESTS_METHOD = "FindAllRequests";
    private static final String FIND_ALL_ANSWERS_METHOD = "FindAllAnswers";
    private static final String FIND_ALL_REQUESTS_DTO_METHOD = "FindAllRequestsDto";
    private static final String FIND_ALL_ANSWERS_DTO_METHOD = "FindAllAnswersDto";

    private Gson gson = GsonFactory.getWCFGson();

    private Context context;

    PetAdopterDao(Context context){
        this.context = context;
    }

    @Override
    public void insert(PetAdopter toInsert, final WsCallback<Object> onResult) {
        HttpHelper.makeRequest(
                String.format("%s/%s/%s", Util.URL, ENDPOINT, INSERT_METHOD),
                HttpHelper.POST,
                gson.toJson(toInsert),
                new HttpHelper.OnResult() {
                    @Override
                    public void execute(Object response) {
                        Object result = null;
                        if(response != null){
                            WCFResponse<Object> res = gson.fromJson(response.toString(), Util.getType(WCFResponse.class, Object.class));
                            result = res.getResponse();
                        }
                        onResult.execute(result);
                    }
                });
    }

    @Override
    public void update(PetAdopter toUpdate, final WsCallback<Boolean> onResult) {
        HttpHelper.makeRequest(
                String.format("%s/%s/%s", Util.URL, ENDPOINT, UPDATE_METHOD),
                HttpHelper.PUT,
                gson.toJson(toUpdate),
                new HttpHelper.OnResult() {
                    @Override
                    public void execute(Object response) {
                        Boolean result = false;
                        if(response != null){
                            WCFResponse<Boolean> res = gson.fromJson(response.toString(), Util.getType(WCFResponse.class, Boolean.class));
                            result = res.getResponse();
                        }
                        onResult.execute(result);
                    }
                });
    }

    @Override
    public void delete(Object id, WsCallback<Boolean> onResult) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void find(Object id, WsCallback<PetAdopter> onResult) {
        throw new RuntimeException("Not implemented");
    }

    public void find(int petId, int adopterId, final WsCallback<PetAdopter> onResult) {
        HttpHelper.makeRequest(
                String.format(Locale.getDefault(), "%s/%s/%s/%d/%d", Util.URL, ENDPOINT, FIND_METHOD, petId, adopterId),
                HttpHelper.GET,
                null,
                new HttpHelper.OnResult() {
                    @Override
                    public void execute(Object response) {
                        PetAdopter result = null;
                        if(response != null){
                            WCFResponse<PetAdopter> res = gson.fromJson(response.toString(), Util.getType(WCFResponse.class, PetAdopter.class));
                            result = res.getResponse();
                        }
                        onResult.execute(result);
                    }
                });
    }

    @Override
    public void findAll(WsCallback<List<PetAdopter>> onResult) {
        throw new RuntimeException("Not implemented");
    }

    public void findAllRequests(int ownerId, final WsCallback<List<PetAdopter>> onResult) {
        HttpHelper.makeRequest(
                String.format(Locale.getDefault(), "%s/%s/%s/%d", Util.URL, ENDPOINT, FIND_ALL_REQUESTS_METHOD, ownerId),
                HttpHelper.GET,
                null,
                new HttpHelper.OnResult() {
                    @Override
                    public void execute(Object response) {
                        List<PetAdopter> result = null;
                        if(response != null){
                            WCFResponse<List<PetAdopter>> res =
                                    gson.fromJson(response.toString(), Util.getType(WCFResponse.class, Util.getType(List.class, PetAdopter.class)));
                            result = res.getResponse();
                        }
                        onResult.execute(result);
                    }
                });
    }

    public void findAllAnswers(int adopterId, final WsCallback<List<PetAdopter>> onResult) {
        HttpHelper.makeRequest(
                String.format(Locale.getDefault(), "%s/%s/%s/%d", Util.URL, ENDPOINT, FIND_ALL_ANSWERS_METHOD, adopterId),
                HttpHelper.GET,
                null,
                new HttpHelper.OnResult() {
                    @Override
                    public void execute(Object response) {
                        List<PetAdopter> result = null;
                        if(response != null){
                            WCFResponse<List<PetAdopter>> res =
                                    gson.fromJson(response.toString(), Util.getType(WCFResponse.class, Util.getType(List.class, PetAdopter.class)));
                            result = res.getResponse();
                        }
                        onResult.execute(result);
                    }
                });
    }

    public void findAllRequestsDto(int ownerId, final WsCallback<List<PetAdopterDto>> onResult) {
        HttpHelper.makeRequest(
                String.format(Locale.getDefault(), "%s/%s/%s/%d", Util.URL, ENDPOINT, FIND_ALL_REQUESTS_DTO_METHOD, ownerId),
                HttpHelper.GET,
                null,
                new HttpHelper.OnResult() {
                    @Override
                    public void execute(Object response) {
                        List<PetAdopterDto> result = null;
                        if(response != null){
                            WCFResponse<List<PetAdopterDto>> res =
                                    gson.fromJson(response.toString(), Util.getType(WCFResponse.class, Util.getType(List.class, PetAdopterDto.class)));
                            result = res.getResponse();
                        }
                        onResult.execute(result);
                    }
                });
    }

    public void findAllAnswersDto(int adopterId, final WsCallback<List<PetAdopterDto>> onResult) {
        HttpHelper.makeRequest(
                String.format(Locale.getDefault(), "%s/%s/%s/%d", Util.URL, ENDPOINT, FIND_ALL_ANSWERS_DTO_METHOD, adopterId),
                HttpHelper.GET,
                null,
                new HttpHelper.OnResult() {
                    @Override
                    public void execute(Object response) {
                        List<PetAdopterDto> result = null;
                        if(response != null){
                            WCFResponse<List<PetAdopterDto>> res =
                                    gson.fromJson(response.toString(), Util.getType(WCFResponse.class, Util.getType(List.class, PetAdopterDto.class)));
                            result = res.getResponse();
                        }
                        onResult.execute(result);
                    }
                });
    }

}


