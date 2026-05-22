package com.example.sdhzbozi.common.cloudinary;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryImageService {

    private final Cloudinary cloudinary;

    public CloudinaryImageService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public UploadedImage upload(MultipartFile file, String folder) throws IOException {
        Map result = cloudinary.uploader().upload(
                file.getBytes(),
                Map.of(
                        "folder", folder,
                        "resource_type", "image"
                )
        );
        return new UploadedImage (
                result.get("secure_url").toString(),
                result.get("public_id").toString()
        );
    }

}
