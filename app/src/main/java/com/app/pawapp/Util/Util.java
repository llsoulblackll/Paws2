package com.app.pawapp.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Base64;

import com.app.pawapp.Classes.PawPicture;
import com.app.pawapp.DataAccess.DataTransferObject.OwnerDto;
import com.app.pawapp.DataAccess.Entity.Owner;
import com.app.pawapp.Util.Gson.GsonFactory;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.Set;

public final class Util {

    private static final String LOGGED_OWNER_KEY = "LoggedOwner";
    private static final String TOKEN_KEY = "TokenKey";

    //public static final String URL = "http://pawswcf-dev.us-west-1.elasticbeanstalk.com/Service";
    public static final String URL = "http://192.168.1.46:60602/Service";
    public static final String RESPONSE = "Response";
    public static final String RESPONSE_CODE = "ResponseCode";
    public static final String RESPONSE_MESSAGE = "ResponseMessage";

    private Util() {
    }

    public static boolean setToken(String token, Context context){
        return SharedPreferencesHelper.setValue(TOKEN_KEY, token, context);
    }

    private static String getToken(Context context){
        return SharedPreferencesHelper.getValue(TOKEN_KEY, context).toString();
    }

    public static String makeRequestUrl(String url, Context context){
        return String.format("%s?token=%s", url, getToken(context));
    }

    public static Type getType(final Type rawType, final Type ... typesParams){
        return new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return typesParams;
            }

            @Override
            public Type getRawType() {
                return rawType;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };
    }

    public static OwnerDto getLoggedOwner(Context context){
        return GsonFactory.getWCFGson().fromJson(SharedPreferencesHelper.getValue(LOGGED_OWNER_KEY, context).toString(), OwnerDto.class);
    }

    public static boolean setLoggedOwner(OwnerDto owner, Context context){
        return SharedPreferencesHelper.setValue(LOGGED_OWNER_KEY, GsonFactory.getWCFGson().toJson(owner), context);
    }

    public static boolean isLoggedIn(Context context){
        return SharedPreferencesHelper.hasKey(LOGGED_OWNER_KEY, context);
    }

    public static boolean logout(Context context){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .remove(LOGGED_OWNER_KEY)
                .apply();//APPLY MAKES THE CHANGES IN MEMORY AND STARTS AN ASYNC TASK TO PERSIST THEM IN DISK
        return !SharedPreferencesHelper.hasKey(LOGGED_OWNER_KEY, context);
    }

    public static void showAlert(String msg, Context context){
        new AlertDialog.Builder(context)
                .setTitle("Aviso")
                .setMessage(msg)
                .setNeutralButton("OK", null)
                .create()
                .show();
    }

    public static void showAlert(String msg, String title, Context context){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setNeutralButton("OK", null)
                .create()
                .show();
    }

    public static String toBase64(byte[] data) {
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    public static byte[] fromBase64(String data) {
        return Base64.decode(data, Base64.DEFAULT);
    }

    public static byte[] bitmapToBytes(Bitmap bitmap, String extension) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        switch (extension) {
            case "png":
                bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);
                break;
            case "jpeg":
            case "jpg":
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                break;
            case "webp":
                bitmap.compress(Bitmap.CompressFormat.WEBP, 70, baos);
                break;
        }

        return baos.toByteArray();
    }

    public static PawPicture getPictureFromIntent(@NonNull Intent i, Context context) throws IOException {
        if(i.getData() != null) {
            PawPicture pic = new PawPicture();
            Bitmap img = MediaStore.Images.Media.getBitmap(context.getContentResolver(), i.getData());
            String mime = context.getContentResolver().getType(i.getData());
            pic.setUri(i.getData());
            pic.setImage(img);
            if(mime != null)
                pic.setType(mime.substring(mime.indexOf("/") + 1));
            return pic;
        }
        return null;
    }

    public static class PermissionHelper {

        private PermissionHelper(){}

        public static boolean checkPermission(String permission, Context context) {
            return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
        }

        public static int requestPermission(Activity activity, String... permission) {
            int requestCode = new Random().nextInt(Integer.MAX_VALUE);
            ActivityCompat.requestPermissions(activity, permission, requestCode);
            return requestCode;
        }
    }

    public static class HttpHelper {

        private HttpHelper(){}

        public static final String GET = "GET";
        public static final String POST = "POST";
        public static final String PUT = "PUT";
        public static final String DELETE = "DELETE";

        private static final int CONNECTION_TIMEOUT = 420000;

        private static HttpURLConnection urlConnection = null;

        public interface OnResult {
            void execute(Object response);
        }

        private interface OnExecute {
            Object execute(String url);
        }


        //URL OF THE API, HTTP METHOD TO USE, BODY IF ANY, AND A CALLBACK TO BE EXECUTED WHEN IT RETURNS
        public static void makeRequest(String url, final String method, final Object body, OnResult onResult) {

            OnExecute onExecute = new OnExecute() {

                @Override
                public Object execute(String innerUrl) {
                    String res = null;

                    try {
                        OutputStream requestData = null;
                        BufferedReader responseDataReader = null;
                        try {
                            urlConnection = (HttpURLConnection) new URL(innerUrl).openConnection();
                            urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
                            urlConnection.setReadTimeout(CONNECTION_TIMEOUT);
                            urlConnection.setRequestMethod(method);

                            if (body != null && (method.equals(POST) || method.equals(PUT))) {
                                urlConnection.addRequestProperty("Content-Type", "application/json");
                                urlConnection.setDoOutput(true);
                                requestData = urlConnection.getOutputStream();
                                requestData.write(body.toString().getBytes(Charset.forName("UTF-8")));
                            }

                            responseDataReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                            StringBuilder responseBuilder = new StringBuilder();

                            String lineHolder;
                            while ((lineHolder = responseDataReader.readLine()) != null) {
                                responseBuilder.append(lineHolder);
                            }

                            res = responseBuilder.toString();

                        } finally {
                            if (requestData != null)
                                requestData.close();
                            if (responseDataReader != null)
                                responseDataReader.close();
                            if(urlConnection != null)
                                urlConnection.disconnect();
                        }
                    } catch (IOException ex) {
                        //throw new RuntimeException(ex);
                        try {
                            System.out.println(urlConnection.getResponseCode());
                            System.out.println(urlConnection.getResponseMessage());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ex.printStackTrace();
                        return null;
                    }
                    return res;
                }
            };

            new AsyncTaskImpl(onExecute, onResult).execute(url);
        }

        //ON EXECUTE IS THE METHOD TO BE EXECUTED ASYNCHRONOUSLY AND ON RESULT IS EXECUTED WITH THE RESULT OF THAT METHOD AFTER IT IS COMPLETED
        private static class AsyncTaskImpl extends AsyncTask<Object, Object, Object> {

            private OnExecute onExecute;
            private OnResult onResult;


            AsyncTaskImpl(OnExecute onExecute, OnResult onResult) {
                this.onExecute = onExecute;
                this.onResult = onResult;
            }

            @Override
            protected Object doInBackground(Object... params) {
                return onExecute.execute(params[0].toString());
            }

            @Override
            protected void onPostExecute(Object result) {
                onResult.execute(result);
            }
        }
    }

    public static final class SharedPreferencesHelper {

        private SharedPreferencesHelper(){}

        //TODO: MAKE METHOD GENERIC ACCEPTING TYPE ARGS
        //GENERICS FOR STATIC METHODS NEED THEIR OWN GENERIC SIGNATURE
        public static Object getValue(String key, Context context) {
            return PreferenceManager.getDefaultSharedPreferences(context).getAll().get(key);
        }

        @SuppressWarnings("unchecked")
        public static boolean setValue(String key, Object value, Context context) {
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            if (value instanceof String)
                editor.putString(key, value.toString());
            else if (value instanceof Integer)
                editor.putInt(key, (int) value);
            else if (value instanceof Float)
                editor.putFloat(key, (float) value);
            else if (value instanceof Boolean)
                editor.putBoolean(key, (boolean) value);
            else if (value instanceof Long)
                editor.putLong(key, (long) value);
            else if (value instanceof Set<?>)
                editor.putStringSet(key, (Set<String>) value);
            else
                throw new RuntimeException("Only primitives or Set<String> are/is allowed");

            editor.apply();

            return true;
        }

        public static boolean hasKey(String key, Context context){
            return PreferenceManager.getDefaultSharedPreferences(context).contains(key);
        }
    }

    public static final class FragmentHelper {

        private FragmentHelper(){}

        public static void navigate(Class<? extends Fragment> fragmentClass, int containerResource, String tag, FragmentManager fragmentManager) {
            Fragment frag = fragmentManager.findFragmentByTag(tag);
            if (frag != null) {
                if (!frag.isVisible()) {
                    for (Fragment f : fragmentManager.getFragments()) {
                        if (f.isVisible()) {
                            fragmentManager.beginTransaction()
                                    .detach(f)
                                    .attach(frag)
                                    .commit();
                            break;
                        }
                    }
                }
            } else {
                for (Fragment f : fragmentManager.getFragments()) {
                    if (f.isVisible()) {
                        fragmentManager.beginTransaction()
                                .detach(f)
                                .commit();
                        break;
                    }
                }
                try {
                    fragmentManager.beginTransaction()
                            .add(containerResource, fragmentClass.newInstance(), tag)
                            .commit();
                } catch(InstantiationException ex){
                    throw new RuntimeException(ex);
                } catch(IllegalAccessException ex){
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
