package android.app;

import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;

/**
 * Created by johnson on 24/8/16.
 */
public final class LoadedApk {

    public String getPackageName() {
        throw new RuntimeException("Stub!");
    }

    public ApplicationInfo getApplicationInfo() {
        throw new RuntimeException("Stub!");
    }

    public ClassLoader getClassLoader() {
        throw new RuntimeException("Stub!");
    }

    public String getAppDir() {
        throw new RuntimeException("Stub!");
    }

    public String getLibDir() {
        throw new RuntimeException("Stub!");
    }

    public String getResDir() {
        throw new RuntimeException("Stub!");
    }

    public String[] getSplitAppDirs() {
        throw new RuntimeException("Stub!");
    }

    public String[] getSplitResDirs() {
        throw new RuntimeException("Stub!");
    }

    public String[] getOverlayDirs() {
        throw new RuntimeException("Stub!");
    }

    public String getDataDir() {
        throw new RuntimeException("Stub!");
    }

    public File getDataDirFile() {
        throw new RuntimeException("Stub!");
    }

    public AssetManager getAssets(final ActivityThread mainThread) {
        throw new RuntimeException("Stub!");
    }

    public Resources getResources(final ActivityThread mainThread) {
        throw new RuntimeException("Stub!");
    }
}
