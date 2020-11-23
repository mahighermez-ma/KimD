package pro.kimd;

import android.app.Application;
import android.content.Context;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.config.CoreConfigurationBuilder;
import org.acra.config.MailSenderConfigurationBuilder;
import org.acra.config.ToastConfigurationBuilder;
import org.acra.data.StringFormat;

//import org.acra.ReportingInteractionMode;
//import org.acra.annotation.ReportsCrashes;
//
//
//@ReportsCrashes(mailTo = "qqbangdev@gmail.com", customReportContent = {
//        ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME,
//        ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL,
//        ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT},
//        mode = ReportingInteractionMode.TOAST, resToastText = R.string.crash_toast_text)
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        ACRA.init(this);

    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        CoreConfigurationBuilder builder = new CoreConfigurationBuilder(this);
        builder.setBuildConfigClass(BuildConfig.class).setReportFormat(StringFormat.JSON);
        builder.getPluginConfigurationBuilder(ToastConfigurationBuilder.class).setResText(R.string.crash_toast_text).setEnabled(true);
        builder.getPluginConfigurationBuilder(MailSenderConfigurationBuilder.class).setMailTo("qqbangdev@gmail.com").setEnabled(true);
        ACRA.init(this, builder);
    }
}