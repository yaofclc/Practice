package com.yaofclc.jsformdemo;

import com.yaofclc.jsformdemo.slice.MainAbilitySlice;
import com.yaofclc.jsformdemo.widget.controller.*;
import ohos.aafwk.ability.*;
import ohos.aafwk.content.Intent;
import ohos.global.icu.text.SimpleDateFormat;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.zson.ZSONObject;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 卡片仅支持Click通用事件
 * 1、事件的定义只能是直接命令式
 * 2、事件定义必须包含action字段，用以说明事件类型，并且卡片不支持任何JS语法，JS文件仅用于声明卡片使用的变量和action，
 * action包含跳转事件（router）和消息事件（message）定义在js文件中的actions字段内，跳转事件可以跳转到卡片应用内，消息事件可以将开发者自定义信息传递到卡片应用
 *
 *
 * "actions":{
 *
 *
 *     "routerEventName":{//跳转事件格式
 *         "action":"router",
 *         "abilityName":"com.yaofclc.jsformdemo.MainAbility",//目标Ability名
 *         "params":{//params字段的值可以在目标Ability的onStart方法中通过intent.getStringParams("params")获取
 *             "message":"weather"
 *         }
 *     }，
 *     "messageEventName":{//消息事件格式，当消息事件被触发时，卡片所挂靠的onTriggerFormEvent方法将执行，params中定义的值会以入参的方式传递给开发人员使用
 *         "action":"message",
 *         "params":{
 *             "message":"test date"
 *         }
 *     }
 * }
 *
 */
public class MainAbility extends Ability {
    public static final int DEFAULT_DIMENSION_2X2 = 2;
    public static final int DIMENSION_1X2 = 1;
    public static final int DIMENSION_2X4 = 3;
    public static final int DIMENSION_4X4 = 4;
    private static final int INVALID_FORM_ID = -1;
    private static final HiLogLabel TAG = new HiLogLabel(HiLog.DEBUG, 0x0, MainAbility.class.getName());
    private String topWidgetSlice;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MainAbilitySlice.class.getName());
        if (intentFromWidget(intent)) {
            topWidgetSlice = getRoutePageSlice(intent);
            if (topWidgetSlice != null) {
                setMainRoute(topWidgetSlice);
            }
        }
        stopAbility(intent);
    }

    @Override
    protected ProviderFormInfo onCreateForm(Intent intent) {
        HiLog.info(TAG, "onCreateForm");

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String time = timeFormat.format(new Date());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());

        ZSONObject zsonObject = new ZSONObject();
        zsonObject.put("date", date);
        zsonObject.put("time", time);
        ProviderFormInfo formInfo = new ProviderFormInfo();
        formInfo.setJsBindingData(new FormBindingData(zsonObject));
        return formInfo;

        /*long formId = intent.getLongParam(AbilitySlice.PARAM_FORM_IDENTITY_KEY, INVALID_FORM_ID);
        String formName = intent.getStringParam(AbilitySlice.PARAM_FORM_NAME_KEY);
        int dimension = intent.getIntParam(AbilitySlice.PARAM_FORM_DIMENSION_KEY, DEFAULT_DIMENSION_2X2);
        HiLog.info(TAG, "onCreateForm: formId=" + formId + ",formName=" + formName);
        FormControllerManager formControllerManager = FormControllerManager.getInstance(this);
        FormController formController = formControllerManager.getController(formId);
        formController = (formController == null) ? formControllerManager.createFormController(formId,
                formName, dimension) : formController;
        if (formController == null) {
            HiLog.error(TAG, "Get null controller. formId: " + formId + ", formName: " + formName);
            return null;
        }
        return formController.bindFormData();*/
    }

    @Override
    protected void onUpdateForm(long formId) {
        HiLog.info(TAG, "onUpdateForm");
        super.onUpdateForm(formId);
        FormControllerManager formControllerManager = FormControllerManager.getInstance(this);
        FormController formController = formControllerManager.getController(formId);
        formController.updateFormData(formId);
    }

    @Override
    protected void onDeleteForm(long formId) {
        HiLog.info(TAG, "onDeleteForm: formId=" + formId);
        super.onDeleteForm(formId);
        FormControllerManager formControllerManager = FormControllerManager.getInstance(this);
        formControllerManager.deleteFormController(formId);
    }

    private boolean triggerTag = false;
    @Override
    protected void onTriggerFormEvent(long formId, String message) {
        HiLog.info(TAG, "onTriggerFormEvent: " + message);
        super.onTriggerFormEvent(formId, message);

        ZSONObject zsonObject = ZSONObject.stringToZSON(message);
        String msg = zsonObject.getString("message");
        if (msg.equalsIgnoreCase("change city")){
            ZSONObject newInfo = new ZSONObject();
            if (triggerTag) {
                newInfo.put("temperature", "15");
                newInfo.put("city", "北京");
            } else {
                newInfo.put("temperature", "16");
                newInfo.put("city", "上海");
            }
            triggerTag = !triggerTag;
            FormBindingData formBindingData = new FormBindingData(newInfo);
            try {
                updateForm(formId, formBindingData);
            } catch (FormException e) {
                e.printStackTrace();
            }

        }

        /*FormControllerManager formControllerManager = FormControllerManager.getInstance(this);
        FormController formController = formControllerManager.getController(formId);
        formController.onTriggerFormEvent(formId, message);*/
    }

    @Override
    public void onNewIntent(Intent intent) {
        if (intentFromWidget(intent)) { // Only response to it when starting from a service widget.
            String newWidgetSlice = getRoutePageSlice(intent);
            if (topWidgetSlice == null || !topWidgetSlice.equals(newWidgetSlice)) {
                topWidgetSlice = newWidgetSlice;
                restart();
            }
        }
    }

    private boolean intentFromWidget(Intent intent) {
        long formId = intent.getLongParam(AbilitySlice.PARAM_FORM_IDENTITY_KEY, INVALID_FORM_ID);
        return formId != INVALID_FORM_ID;
    }

    private String getRoutePageSlice(Intent intent) {
        long formId = intent.getLongParam(AbilitySlice.PARAM_FORM_IDENTITY_KEY, INVALID_FORM_ID);
        if (formId == INVALID_FORM_ID) {
            return null;
        }
        FormControllerManager formControllerManager = FormControllerManager.getInstance(this);
        FormController formController = formControllerManager.getController(formId);
        if (formController == null) {
            return null;
        }
        Class<? extends AbilitySlice> clazz = formController.getRoutePageSlice(intent);
        if (clazz == null) {
            return null;
        }
        return clazz.getName();
    }
}
