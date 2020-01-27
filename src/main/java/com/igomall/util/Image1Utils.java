
package com.igomall.util;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import net.coobird.thumbnailator.Thumbnails;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.net.URL;

/**
 * Utils - 图片处理
 * 
 * @author blackboy
 * @version 1.0
 */
public final class Image1Utils {

	public static void zoom(File srcFile,File destFile,Integer width,Integer height){
		try {
			Thumbnails.of(srcFile)
					.forceSize(width, height)
					.toFile(destFile);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		Metadata metadata = JpegMetadataReader.readMetadata(new URL("https://mmbiz.qpic.cn/mmbiz_jpg/GvtDGKK4uYmccecPPye2Z3icmGcibM1Fu0xM1eiaCVpNf5qT1KcGNqPcCRalKKqQWDDvvLdnOmO7jTWicZlBqp1iaCg/640").openStream());
		for(Directory directory : metadata.getDirectories()){
			for(Tag tag : directory.getTags()){
				System.out.print("name : " + tag.getTagName() + "  -->");
				System.out.println("desc : " + tag.getDescription());
			}
		}
	}

}