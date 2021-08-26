/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codes.angeljsb.pixelswizard;

import codes.angeljsb.capsulator.BaseFile;
import codes.angeljsb.capsulator.Directory;
import codes.angeljsb.capsulator.image.ImageFile;
import codes.angeljsb.capsulator.image.ImageLoader;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Angel
 */
public class Converter {
    
    private Directory target;
    private Directory root;
    private boolean convertAll;
    private boolean replace;
    private ImageFile[] sources;
    private int width = -1, height = -1;
    private String imageType = "";
    private int rotate = 0;
    
    public Converter(String root, String target) {
        this.root = new Directory(new File(root).getAbsolutePath());
        this.target = new Directory(target);
        this.target.mkdirs();
    }

    public Directory getTarget() {
        return target;
    }

    public void setTarget(Directory target) {
        this.target = target;
    }

    public Directory getRoot() {
        return root;
    }

    public void setRoot(Directory root) {
        this.root = root;
    }

    public boolean isConvertAll() {
        return convertAll;
    }

    public void setConvertAll(boolean convertAll) {
        this.convertAll = convertAll;
    }

    public boolean isReplace() {
        return replace;
    }

    public void setReplace(boolean replace) {
        this.replace = replace;
    }

    public ImageFile[] getSources() {
        return sources;
    }

    public void setSources(ImageFile[] sources) {
        this.sources = sources;
    }
    
    public void setSources(List<File> sources) {
        this.sources = sources.stream()
                .filter(file -> file.isFile())
                .map(BaseFile::new)
                .filter(base -> base.compareFormat(ImageFile.ACCEPTED_FORMATS))
                .map(ImageFile::new)
                .toArray(ImageFile[]::new);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public int getRotate() {
        return rotate;
    }

    public void setRotate(int rotate) {
        this.rotate = rotate;
    }
    
    public void processImages() {
        if(convertAll) {
            File[] dirFiles = root.listBaseFiles(ImageFile.ACCEPTED_FORMATS);
            sources = new ImageFile[dirFiles.length];
            for(int i=0; i<dirFiles.length; i++) {
                sources[i] = new ImageFile(new BaseFile(dirFiles[i]));
            }
        }
        
        for(ImageFile image : sources) {
            image.read();
            String name = image.getBaseFile().getName();
            
            if(width > 0 || height > 0) {
                System.out.println("Resizing " + name + " ...");
                image.resize(width, height);
                System.out.println(name + " successfully resized");
            }
            
            if(rotate > 0 && rotate < 360) {
                System.out.println("Rotating " + name + " ...");
                image.rotate(rotate);
                System.out.println(name + " successfully rotated");
            }
            
            if(!imageType.isEmpty()) {
                int type = image.get().getType();
                switch (imageType) {
                    case "gray":
                        type = BufferedImage.TYPE_BYTE_GRAY;
                        break;
                    case "byte-indexed":
                        type = BufferedImage.TYPE_BYTE_INDEXED;
                        break;
                    case "3bytes":
                        type = BufferedImage.TYPE_3BYTE_BGR;
                        break;
                    case "4bytes":
                        type = BufferedImage.TYPE_4BYTE_ABGR;
                        break;
                    case "rgba":
                        type = BufferedImage.TYPE_INT_ARGB;
                        break;
                    case "rgb":
                        type = BufferedImage.TYPE_INT_RGB;
                        break;
                    default:
                        break;
                }
                if(type != image.get().getType()){
                    System.out.println("Changing image type of " + name + " to " + type);
                    BufferedImage prev = image.get();
                    BufferedImage next = new BufferedImage(prev.getWidth(), prev.getHeight(), type);
                    next.getGraphics().drawImage(prev, 0, 0, null);
                    image.setContent(next);
                    System.out.println("Type successfully changed");
                }
            }
            
            if(replace) {
                System.out.println("Replacing " + name + " with the new values");
                image.write();
                System.out.println(name + " successfully replaced");
            } else {
                BaseFile targetFile = new BaseFile(target, name);
                System.out.println("Saving " + targetFile.getAbsolutePath() + " ...");
                boolean alreadyExists = false;
                try {
                    alreadyExists = !targetFile.createNewFile();
                } catch (IOException ex) {
                    System.err.println("The target file cannot be created");
                }
                if(alreadyExists) {
                    System.err.println("The target file already exists."
                            + " The writening was stopped in order of"
                            + " prevent to override information");
                    continue;
                }
                ImageFile targetImage = new ImageFile(targetFile);
                targetImage.setContent(image.getContent());
                
                targetImage.write();
                System.out.println(name + " saved");
            }
            
            image.setContent(null);
        }
    }
    
}
