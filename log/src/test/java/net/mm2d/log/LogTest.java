/*
 * Copyright (c) 2016 大前良介 (OHMAE Ryosuke)
 *
 * This software is released under the MIT License.
 * http://opensource.org/licenses/MIT
 */

package net.mm2d.log;

import net.mm2d.log.Log.Print;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javax.annotation.Nullable;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SuppressWarnings("NonAsciiCharacters")
@RunWith(JUnit4.class)
public class LogTest {
    @Before
    public void before() {
        Log.setLogLevel(Log.VERBOSE);
    }

    @Test
    public void v_VERBOSEレベルでコールされること() {
        final String tag = "TAG";
        final String message = "MESSAGE";
        final Throwable throwable = new Throwable();

        Print print = spy(Print.class);
        Log.setPrint(print);
        Log.v(tag, message);
        verify(print).println(Log.VERBOSE, tag, message);

        print = mock(Print.class);
        Log.setPrint(print);
        Log.v(tag, message, throwable);
        verify(print).println(eq(Log.VERBOSE), eq(tag), contains(message));
    }

    @Test
    public void d_DEBUGレベルでコールされること() {
        final String tag = "TAG";
        final String message = "MESSAGE";
        final Throwable throwable = new Throwable();

        Print print = mock(Print.class);
        Log.setPrint(print);
        Log.d(tag, message);
        verify(print).println(Log.DEBUG, tag, message);

        print = mock(Print.class);
        Log.setPrint(print);
        Log.d(tag, message, throwable);
        verify(print).println(eq(Log.DEBUG), eq(tag), contains(message));
    }

    @Test
    public void i_INFOレベルでコールされること() {
        final String tag = "TAG";
        final String message = "MESSAGE";
        final Throwable throwable = new Throwable();

        Print print = mock(Print.class);
        Log.setPrint(print);
        Log.i(tag, message);
        verify(print).println(Log.INFO, tag, message);

        print = mock(Print.class);
        Log.setPrint(print);
        Log.i(tag, message, throwable);
        verify(print).println(eq(Log.INFO), eq(tag), contains(message));
    }

    @Test
    public void w_WARNレベルでコールされること() {
        final String tag = "TAG";
        final String message = "MESSAGE";
        final Throwable throwable = new Throwable();

        Print print = mock(Print.class);
        Log.setPrint(print);
        Log.w(tag, message);
        verify(print).println(Log.WARN, tag, message);

        print = mock(Print.class);
        Log.setPrint(print);
        Log.w(tag, message, throwable);
        verify(print).println(eq(Log.WARN), eq(tag), contains(message));

        print = mock(Print.class);
        Log.setPrint(print);
        Log.w(tag, throwable);
        verify(print).println(eq(Log.WARN), eq(tag), anyString());
    }

    @Test
    public void e_ERRORレベルでコールされること() {
        final String tag = "TAG";
        final String message = "MESSAGE";
        final Throwable throwable = new Throwable();

        Print print = mock(Print.class);
        Log.setPrint(print);
        Log.e(tag, message);
        verify(print).println(Log.ERROR, tag, message);

        print = mock(Print.class);
        Log.setPrint(print);
        Log.e(tag, message, throwable);
        verify(print).println(eq(Log.ERROR), eq(tag), contains(message));
    }

    @Test
    public void setLogLevel_指定したレベル以上のログのみ出力される() {
        final String tag = "TAG";
        final String message = "MESSAGE";
        final Throwable throwable = new Throwable();
        Print print;

        Log.setLogLevel(Log.VERBOSE);
        print = mock(Print.class);
        Log.setPrint(print);
        Log.v(message);
        Log.v(throwable);
        Log.v(tag, message);
        Log.v(tag, message, throwable);
        Log.d(message);
        Log.d(throwable);
        Log.d(tag, message);
        Log.d(tag, message, throwable);
        Log.i(message);
        Log.i(throwable);
        Log.i(tag, message);
        Log.i(tag, message, throwable);
        Log.w(message);
        Log.w(throwable);
        Log.w(tag, message);
        Log.w(tag, message, throwable);
        Log.w(tag, throwable);
        Log.e(message);
        Log.e(throwable);
        Log.e(tag, message);
        Log.e(tag, message, throwable);
        verify(print, times(21)).println(anyInt(), anyString(), anyString());

        Log.setLogLevel(Log.DEBUG);
        print = mock(Print.class);
        Log.setPrint(print);
        Log.v(message);
        Log.v(throwable);
        Log.v(tag, message);
        Log.v(tag, message, throwable);
        verify(print, never()).println(anyInt(), anyString(), anyString());
        Log.d(message);
        Log.d(throwable);
        Log.d(tag, message);
        Log.d(tag, message, throwable);
        Log.i(message);
        Log.i(throwable);
        Log.i(tag, message);
        Log.i(tag, message, throwable);
        Log.w(message);
        Log.w(throwable);
        Log.w(tag, message);
        Log.w(tag, message, throwable);
        Log.w(tag, throwable);
        Log.e(message);
        Log.e(throwable);
        Log.e(tag, message);
        Log.e(tag, message, throwable);
        verify(print, times(17)).println(anyInt(), anyString(), anyString());

        Log.setLogLevel(Log.INFO);
        print = mock(Print.class);
        Log.setPrint(print);
        Log.v(message);
        Log.v(throwable);
        Log.v(tag, message);
        Log.v(tag, message, throwable);
        Log.d(message);
        Log.d(throwable);
        Log.d(tag, message);
        Log.d(tag, message, throwable);
        verify(print, never()).println(anyInt(), anyString(), anyString());

        Log.i(message);
        Log.i(throwable);
        Log.i(tag, message);
        Log.i(tag, message, throwable);
        Log.w(message);
        Log.w(throwable);
        Log.w(tag, message);
        Log.w(tag, message, throwable);
        Log.w(tag, throwable);
        Log.e(message);
        Log.e(throwable);
        Log.e(tag, message);
        Log.e(tag, message, throwable);
        verify(print, times(13)).println(anyInt(), anyString(), anyString());

        Log.setLogLevel(Log.WARN);
        print = mock(Print.class);
        Log.setPrint(print);
        Log.v(message);
        Log.v(throwable);
        Log.v(tag, message);
        Log.v(tag, message, throwable);
        Log.d(message);
        Log.d(throwable);
        Log.d(tag, message);
        Log.d(tag, message, throwable);
        Log.i(message);
        Log.i(throwable);
        Log.i(tag, message);
        Log.i(tag, message, throwable);
        verify(print, never()).println(anyInt(), anyString(), anyString());

        Log.w(message);
        Log.w(throwable);
        Log.w(tag, message);
        Log.w(tag, message, throwable);
        Log.w(tag, throwable);
        Log.e(message);
        Log.e(throwable);
        Log.e(tag, message);
        Log.e(tag, message, throwable);
        verify(print, times(9)).println(anyInt(), anyString(), anyString());

        Log.setLogLevel(Log.ERROR);
        print = mock(Print.class);
        Log.setPrint(print);
        Log.v(message);
        Log.v(throwable);
        Log.v(tag, message);
        Log.v(tag, message, throwable);
        Log.d(message);
        Log.d(throwable);
        Log.d(tag, message);
        Log.d(tag, message, throwable);
        Log.i(message);
        Log.i(throwable);
        Log.i(tag, message);
        Log.i(tag, message, throwable);
        Log.w(message);
        Log.w(throwable);
        Log.w(tag, message);
        Log.w(tag, message, throwable);
        Log.w(tag, throwable);
        verify(print, never()).println(anyInt(), anyString(), anyString());

        Log.e(message);
        Log.e(throwable);
        Log.e(tag, message);
        Log.e(tag, message, throwable);
        verify(print, times(4)).println(anyInt(), anyString(), anyString());

        Log.setLogLevel(Log.ASSERT);
        print = mock(Print.class);
        Log.setPrint(print);
        Log.v(message);
        Log.v(throwable);
        Log.v(tag, message);
        Log.v(tag, message, throwable);
        Log.d(message);
        Log.d(throwable);
        Log.d(tag, message);
        Log.d(tag, message, throwable);
        Log.i(message);
        Log.i(throwable);
        Log.i(tag, message);
        Log.i(tag, message, throwable);
        Log.w(message);
        Log.w(throwable);
        Log.w(tag, message);
        Log.w(tag, message, throwable);
        Log.w(tag, throwable);
        Log.e(message);
        Log.e(throwable);
        Log.e(tag, message);
        Log.e(tag, message, throwable);
        verify(print, never()).println(anyInt(), anyString(), anyString());
    }

    @Test
    public void v_Tagがnullの場合メソッド名が使われる() {
        Log.setPrint(Log.getDefaultPrint());
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final PrintStream defaultOut = System.out;
        System.setOut(new PrintStream(baos));
        Log.v(null, "");

        assertThat(new String(baos.toByteArray()), containsString("LogTest"));
        System.setOut(defaultOut);
    }

    @Test
    public void println_ログレベルの文字列が含まれる() {
        Log.setPrint(Log.getDefaultPrint());
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final PrintStream defaultOut = System.out;
        System.setOut(new PrintStream(baos));

        Log.v("tag", "message");
        assertThat(new String(baos.toByteArray()), containsString(" V "));
        baos.reset();

        Log.d("tag", "message");
        assertThat(new String(baos.toByteArray()), containsString(" D "));
        baos.reset();

        Log.i("tag", "message");
        assertThat(new String(baos.toByteArray()), containsString(" I "));
        baos.reset();

        Log.w("tag", "message");
        assertThat(new String(baos.toByteArray()), containsString(" W "));
        baos.reset();

        Log.e("tag", "message");
        assertThat(new String(baos.toByteArray()), containsString(" E "));
        baos.reset();

        Log.e("tag", "message");
        assertThat(new String(baos.toByteArray()), containsString(" E "));
        baos.reset();

        Log.println(Log.ASSERT, "tag", "message", null);
        assertThat(new String(baos.toByteArray()), containsString("   "));
        baos.reset();

        System.setOut(defaultOut);
    }

    @Test
    public void print() {
        final Print print = new Print() {
            @Override
            public void println(
                    final int level,
                    @Nullable final String tag,
                    @Nullable final String message) {
            }
        };
        Log.setPrint(print);
        Log.v(null, "");
    }

    @Test
    public void setAppendCaller() throws Exception {
        Log.setPrint(Log.getDefaultPrint());
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final PrintStream defaultOut = System.out;
        System.setOut(new PrintStream(baos));
        Log.appendCaller(true);

        Log.v("tag", "");
        assertThat(new String(baos.toByteArray()), containsString("LogTest"));
        baos.reset();

        Log.appendCaller(false);

        Log.v("tag", "");
        assertThat(new String(baos.toByteArray()), not(containsString("LogTest")));
        baos.reset();
        System.setOut(defaultOut);
    }

    @Test
    public void setAppendThread() throws Exception {
        Log.setPrint(Log.getDefaultPrint());
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final PrintStream defaultOut = System.out;
        System.setOut(new PrintStream(baos));
        Log.appendThread(true);
        Log.v("tag", "");
        assertThat(new String(baos.toByteArray()), containsString(Thread.currentThread().getName()));
        baos.reset();

        Log.appendThread(false);

        Log.v("tag", "");
        assertThat(new String(baos.toByteArray()), not(containsString(Thread.currentThread().getName())));
        baos.reset();
        System.setOut(defaultOut);
    }
}
