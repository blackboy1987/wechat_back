package com.igomall.util;

import com.drew.imaging.avi.AviMetadataReader;
import com.drew.imaging.bmp.BmpMetadataReader;
import com.drew.imaging.eps.EpsMetadataReader;
import com.drew.imaging.gif.GifMetadataReader;
import com.drew.imaging.heif.HeifMetadataReader;
import com.drew.imaging.ico.IcoMetadataReader;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.mp3.Mp3MetadataReader;
import com.drew.imaging.mp4.Mp4MetadataReader;
import com.drew.imaging.pcx.PcxMetadataReader;
import com.drew.imaging.png.PngMetadataReader;
import com.drew.imaging.psd.PsdMetadataReader;
import com.drew.imaging.quicktime.QuickTimeMetadataReader;
import com.drew.imaging.raf.RafMetadataReader;
import com.drew.imaging.tiff.TiffMetadataReader;
import com.drew.imaging.wav.WavMetadataReader;
import com.drew.imaging.webp.WebpMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class MediaUtils {

    private MediaUtils(){}

    public static Metadata getMetadata(File file){
        Metadata metadata = null;
        try {
            String extension = FilenameUtils.getExtension(file.getName());
            switch (extension.toLowerCase()){
                case "avi":
                    metadata = AviMetadataReader.readMetadata(file);
                    break;
                case "bmp":
                    metadata = BmpMetadataReader.readMetadata(file);
                    break;
                case "eps":
                case "epsf":
                case "epsi":
                    metadata = EpsMetadataReader.readMetadata(file);
                    break;
                case "gif":
                    metadata = GifMetadataReader.readMetadata(file);
                    break;
                case "heif":
                case "heic":
                    metadata = HeifMetadataReader.readMetadata(new FileInputStream(file));
                    break;
                case "ico":
                    metadata = IcoMetadataReader.readMetadata(file);
                    break;
                case "jpeg":
                case "jpg":
                case "jpe":
                    metadata = JpegMetadataReader.readMetadata(file);
                    break;
                case "mov":
                    metadata = QuickTimeMetadataReader.readMetadata(file);
                    break;
                case "qt":
                    metadata = null;
                    break;
                case "mp3":
                    metadata = Mp3MetadataReader.readMetadata(file);
                    break;
                case "mp4":
                case "m4a":
                case "m4p":
                case "m4b":
                case "m4r":
                case "m4v":
                    metadata = Mp4MetadataReader.readMetadata(file);
                    break;
                case "pcx":
                    metadata = PcxMetadataReader.readMetadata(file);
                    break;
                case "png":
                    metadata = PngMetadataReader.readMetadata(file);
                    break;
                case "psd":
                    metadata = PsdMetadataReader.readMetadata(file);
                    break;
                case "raf":
                    metadata = RafMetadataReader.readMetadata(file);
                    break;
                case "riff":
                    metadata = null;
                    break;
                case "tiff":
                case "tif":
                    metadata = TiffMetadataReader.readMetadata(file);
                    break;
                case "wav":
                case "wave":
                    metadata = WavMetadataReader.readMetadata(file);
                    break;
                case "webp":
                    metadata = WebpMetadataReader.readMetadata(file);
                    break;
                default:
                    metadata = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return metadata;
    }

    public static Map<String,String> getInfo(File file) {
        Metadata metadata = getMetadata(file);
        if(metadata==null){
            return null;
        }

        Map<String,String> map = new HashMap<>();
        Map<String,String> map1 = new HashMap<>();
        Iterator<Directory> directories =  metadata.getDirectories().iterator();
        while (directories.hasNext()){
            Directory directory = directories.next();
            Collection<Tag> list = directory.getTags();
            for (Tag tag:list) {
                map.put(tag.getTagName().replace(" ","_"),tag.getDescription());
            }
        }
        for (String key:map.keySet()) {
            if(StringUtils.equalsAnyIgnoreCase("Duration",key)){
                // 文件（视频）时长
                map1.put("duration",map.get(key));
            }else if(StringUtils.equalsAnyIgnoreCase("Width",key)){
                // 文件（图片，视频）宽度
                map1.put("width",map.get(key).replace(" pixels",""));
            }else if(StringUtils.equalsAnyIgnoreCase("Height",key)){
                // 文件（图片，视频）宽度
                map1.put("height",map.get(key).replace(" pixels",""));
            }else if(StringUtils.equalsAnyIgnoreCase("File_Size",key)){
                // 文件大小
                map1.put("size",map.get(key).replace(" bytes",""));
            }
        }
        return map1;
    }

}
