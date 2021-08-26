# PixelsWizard
Herramienta de linea de comandos que puede realizar acciones automatizadas sobre grupos de imágenes guardadas en archivos. Las acciones que se pueden realizar incluyen rotación, resizing y cambio de tipo de imagen

## Comandos
PixelsWizard takes the images you pass in te arguments and copy then in the target folder applying the changes you specify with the following commands:
<ul>   
   <li> "<b>-w</b>": Specifies the width of the copy</li>
   <li> "<b>-h</b>": Specifies the height of the copy</li>
   Note: If you specify only one size argument (-w or -h), the image will be resized to maintain the aspect ratio. 
   If you do not specify one, the image will not be resized. 
   <li> "<b>-i</b>": specifies the image type of the copy. That must be one of the followings:
   <ul>
         <li>"gray": For a byte-gray type image. In grayscale</li>
         <li>"byte-indexed": For a byte-indexed type image. Image type that has only 256 possible pixel colors.</li>              
         <li>"3bytes": For a three bytes BGR image without transparency</li> 
         <li>"4bytes": For a four bytes ABGR image with possibility of transparency</li>
         <li>"rgb": For a integer rgb image type without transparency</li>
         <li>"argb": For a integer argb image type with transparency</li>
                 </ul>
</li>
    <li>"<b>-r</b>": Specifies a quantity of degrees to rotate the image (from 1 to 359)</li>
    <li>"<b>-t</b>": To change the target directory in which save the copies</li>
    <li>"<b>--help</b>": To show a help message (Should be the first argument)</li>
    <li>"<b>--all</b>": Apply the changes and copy to the target directory all the images in the current folder</li>
    <li>"<b>--replace</b>": Replace the images instead of copying them to the target directory</li>
</ul>
