package com.app.pawapp.DataAccess.DataAccessObject;

import android.content.Context;

import com.app.pawapp.DataAccess.DataTransferObject.PetDto;
import com.app.pawapp.DataAccess.Entity.Pet;
import com.app.pawapp.Util.Gson.GsonFactory;
import com.app.pawapp.Util.Util;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PetDao implements Ws<Pet>{

    private static final String ENDPOINT = "PetService.svc";

    private static final String INSERT_METHOD = "New";
    private static final String UPDATE_METHOD = "Update";
    private static final String DELETE_METHOD = "Delete";
    private static final String FIND_METHOD = "Find";
    private static final String FIND_ALL_METHOD = "FindAll";
    private static final String FIND_ALL_DTO_METHOD = "FindAllDto";
    private static final String LOGIN_METHOD = "Login";

    private Context context;

    private Gson gson;

    PetDao(Context context){
        this.context = context;
        gson = GsonFactory.getWCFGson();
    }

    @Override
    public void insert(Pet toInsert, final WsCallback<Object> onResult) {
        Util.HttpHelper.makeRequest(String.format("%s/%s/%s", Util.URL, ENDPOINT, INSERT_METHOD),
                Util.HttpHelper.POST,
                gson.toJson(toInsert),
                new Util.HttpHelper.OnResult() {
                    @Override
                    public void execute(Object response) {
                        Integer id = 0;
                        if(response != null){
                            WCFResponse<Object> result = gson.fromJson(response.toString(), Util.getType(WCFResponse.class, Object.class));
                            id = (int)(double)result.getResponse();
                        }
                        onResult.execute(id);
                    }
                });
    }

    @Override
    public void update(Pet toUpdate, final WsCallback<Boolean> onResult) {
        System.out.println(gson.toJson(toUpdate));
        Util.HttpHelper.makeRequest(
                String.format("%s/%s/%s", Util.URL, ENDPOINT, UPDATE_METHOD),
                Util.HttpHelper.PUT,
                gson.toJson(toUpdate),
                new Util.HttpHelper.OnResult() {
                    @Override
                    public void execute(Object response) {
                        Boolean result = false;
                        if(response != null){
                            WCFResponse<Boolean> res = gson.fromJson(response.toString(), Util.getType(WCFResponse.class, Boolean.class));
                            result = res.getResponse();
                        }
                        onResult.execute(result);
                    }
                }
        );
    }

    @Override
    public void delete(Object id, final WsCallback<Boolean> onResult) {
        Util.HttpHelper.makeRequest(
                String.format("%s/%s/%s/%s", Util.URL, ENDPOINT, DELETE_METHOD, id),
                Util.HttpHelper.DELETE,
                null,
                new Util.HttpHelper.OnResult() {
                    @Override
                    public void execute(Object response) {
                        Boolean result = false;
                        if(response != null){
                            WCFResponse<Boolean> res = gson.fromJson(response.toString(), Util.getType(WCFResponse.class, Boolean.class));
                            result = res.getResponse();
                        }
                        onResult.execute(result);
                    }
                }
        );
    }

    @Override
    public void find(Object id, WsCallback<Pet> onResult) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void findAll(final WsCallback<List<Pet>> onResult) {
        Util.HttpHelper.makeRequest(String.format("%s/%s/%s", Util.URL, ENDPOINT, FIND_ALL_METHOD),
                Util.HttpHelper.GET,
                null,
                new Util.HttpHelper.OnResult() {
                    @Override
                    public void execute(Object response) {
                        List<Pet> lPets = null;
                        if(response != null) {
                            WCFResponse<List<Pet>> pets = gson.fromJson(response.toString(), Util.getType(WCFResponse.class, Util.getType(List.class, Pet.class)));
                            lPets = pets.getResponse();
                        }
                        onResult.execute(lPets);
                    }
                });
    }

    public void findAll(int ownerId, final WsCallback<List<Pet>> onResult){
        Util.HttpHelper.makeRequest(
                String.format(Locale.getDefault(),"%s/%s/%s/%d", Util.URL, ENDPOINT, FIND_ALL_METHOD, ownerId),
                Util.HttpHelper.GET,
                null,
                new Util.HttpHelper.OnResult() {
                    @Override
                    public void execute(Object response) {
                        List<Pet> lPets = null;
                        if(response != null){
                            WCFResponse<List<Pet>> pets = gson.fromJson(response.toString(), Util.getType(WCFResponse.class, Util.getType(List.class, Pet.class)));
                            lPets = pets.getResponse();
                        }
                        onResult.execute(lPets);
                    }
                });
    }

    public void findAllDto(final WsCallback<List<PetDto>> onResult){
        Util.HttpHelper.makeRequest(
                String.format("%s/%s/%s", Util.URL, ENDPOINT, FIND_ALL_DTO_METHOD),
                Util.HttpHelper.GET,
                null,
                new Util.HttpHelper.OnResult() {
                    @Override
                    public void execute(Object response) {
                        List<PetDto> lPets = null;
                        if(response != null){
                            WCFResponse<List<PetDto>> pets = gson.fromJson(response.toString(), Util.getType(WCFResponse.class, Util.getType(List.class, PetDto.class)));
                            lPets = pets.getResponse();
                        }
                        onResult.execute(lPets);
                    }
                });
    }

    public void findAllDto(int ownerId, boolean ownPets, final WsCallback<List<PetDto>> onResult){
        Util.HttpHelper.makeRequest(
                String.format(Locale.getDefault(),"%s/%s/%s/%d/%b", Util.URL, ENDPOINT, FIND_ALL_DTO_METHOD, ownerId, ownPets),
                Util.HttpHelper.GET,
                null,
                new Util.HttpHelper.OnResult() {
                    @Override
                    public void execute(Object response) {
                        List<PetDto> lPets = null;
                        if(response != null){
                            WCFResponse<List<PetDto>> pets = gson.fromJson(response.toString(), Util.getType(WCFResponse.class, Util.getType(List.class, PetDto.class)));
                            lPets = pets.getResponse();
                        }
                        onResult.execute(lPets);
                    }
                });
    }
}
