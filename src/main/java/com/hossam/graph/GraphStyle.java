package com.hossam.graph;

public class GraphStyle {

    public static String styleSheet =
            "node {" +
                    "   text-color: white;" +
                    "   text-style: bold;" +
                    "   text-size: 14px;" +
                    "   text-background-mode: rounded-box;" +
                    "   text-background-color: rgba(182,34,79, 200);" +
                    "   text-padding: 5px;" +
                    "   text-offset: 0px, 2px;" +
                    "   shape: circle;" +
                    "   size: 25px;" +
                    "   text-alignment: above;" +
                    "   text-offset: 0px, -12px;" +
                    "   fill-color: rgba(182,34,79, 100);" +
                    "   stroke-color: rgba(182,34,79, 100);" +
                    "   stroke-mode: plain;" +
                    "   stroke-width: 4px;" +
                    "   shadow-mode: gradient-radial;" +
                    "   shadow-width: 4px;" +
                    "   shadow-color: #999, white;" +
                    "   shadow-offset: 0px, -1px;" +
                    "   z-index: 1;" +
                    "}" +
                    "edge {" +
//                    "   shape: cubic-curve;" +
                    "   size: 3px;" +
                    "   fill-color: rgba(52,68,91, 150);" +
                    "   text-color: rgba(52,68,91, 200);" +
                    "   text-style: bold;" +
                    "   text-background-mode: rounded-box;" +
                    "   text-background-color: rgba(208,245,91, 250);" +
                    "   text-padding: 2px;" +
                    "   text-size: 14px;" +
//                    "   stroke-mode: plain;" +
//                    "   stroke-color: rgba(52,68,91, 150);" +
//                    "   stroke-width: 1px;" +
                    "   z-index: 0;" +
                    "}";

    public static String styleSheetEdges =
//                    "   shape: cubic-curve;" +
            "   size: 3px;" +
                    "   fill-color: rgba(0,255,204, 200);" +
                    "   stroke-mode: plain;" +
                    "   stroke-color: rgba(0,255,204, 200);" +
                    "   stroke-width: 1px;" +
                    "   z-index: 0;";
}
