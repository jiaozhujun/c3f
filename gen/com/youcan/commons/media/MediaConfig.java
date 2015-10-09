package com.youcan.commons.media;
public class MediaConfig {
public static String supportPath="C:/webserver/xingbookSupport";
public static final class smiEngine {
public static String cmd="C:/webserver/xingbookSupport/mi/MediaInfo.exe";
}
public static final class sifEngine {
public static String cmd="C:/webserver/xingbookSupport/im-static/convert.exe";
public static String defaultSize="160x160>";
public static String defaultOptions="";
}
public static final class svfEngine {
public static String cmd="C:/webserver/xingbookSupport/ff/ffmpeg.exe";
}
public static final class VideoConvert {
public static int defaultWidth=400;
public static int defaultHeight=0;
public static int defaultBitRate=200000;
}
public static final class AudioConvert {
public static String BitRate="96k";
public static int SampleRate=48000;
}
public static final class PDF2Images {
public static String cmd="C:/webserver/xingbookSupport/xpdf/pdfimages.exe";
}
}
