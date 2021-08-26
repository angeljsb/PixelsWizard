/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codes.angeljsb.pixelswizard;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Angel
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Gracias por usar PixelsWizard");
        
        if(args.length == 0 || args[0].equals("--help")) {
            System.out.println();
            System.out.print(
            "PixelsWizard takes the images you pass in te arguments "
                    + "and copy then in the target folder applying "
                    + "the changes yo specify with the following commands: \n"
                    + "\t -w: Specifies the width of the copy\n"
                    + "\t -h: Specifies the height of the copy\n"
                    + "Note: If you specify only one size argument (-w or -h), "
                    + "the image will be resized to maintain the aspect ratio. "
                    + "If you do not specify one, the image will not be resized. \n"
                    + "\t -i: specifies the image type of the copy. That must be one "
                    + "of the followings:\n"
                    + "\t\t \"gray\": For a byte-gray type image. In grayscale\n"
                    + "\t\t \"byte-indexed\": For a byte-indexed type image. "
                    + "Image type that has only 256 possible pixel colors."
                    + "\t\t \"3bytes\": For a three bytes BGR image without "
                    + "transparency\n"
                    + "\t\t \"4bytes\": For a four bytes ABGR image with possibility "
                    + "of transparency\n"
                    + "\t\t \"rgb\": For a integer rgb image type without transparency\n"
                    + "\t\t \"argb\": For a integer argb image type with transparency\n"
                    + "\n"
                    + "\t -r: Specifies a quantity of degrees to rotate the image (from 1 to 359)\n"
                    + "\t -t: To change the target directory in which save the copies"
                    + "\n"
                    + "\t --help: To show this help message (Should be the first argument)\n"
                    + "\t --all: Apply the changes and copy to the target directory all the images "
                    + "in the current folder\n"
                    + "\t --replace: Replace the images instead of copying them to the target directory"
            );
            System.out.println();
            return;
        }
        
        HashMap<String, String> params = new HashMap();
        ArrayList<File> files = new ArrayList();
        boolean allFiles = false;
        boolean replace = false;
        
        for(int i=0; i<args.length; i++) {
            String arg = args[i];
            if(arg.startsWith("-") && arg.length() == 2) {
                i++;
                params.put(arg, args[i]);
                continue;
            }
            if(arg.equals("--all")){
                allFiles = true;
                continue;
            }
            if(arg.equals("--replace")){
                replace = true;
                continue;
            }
            files.add(new File(arg));
        }
        
        String target = params.get("-t") == null ? "copy" : params.get("-t");
        
        String width = params.get("-w");
        String height = params.get("-h");
        String type = params.get("-i");
        String rotate = params.get("-r");
        
        Converter converter = new Converter("", target);
        converter.setConvertAll(allFiles);
        converter.setReplace(replace);
        converter.setSources(files);
        
        if(width != null) {
            converter.setWidth(Integer.parseInt(width));
        }
        if(height != null) {
            converter.setHeight(Integer.parseInt(height));
        }
        if(type != null) {
            converter.setImageType(type);
        }
        if(rotate != null) {
            converter.setRotate(Integer.parseInt(rotate));
        }
        
        converter.processImages();
        
    }
    
}
