//package raytracer;
//
//import javafx.geometry.BoundingBox;
//
//import java.util.ArrayList;
//
///**
// * Created by Anton on 22.05.2014.
// */
//public class KdTree {
//    private static int MAX_TREE_DEPTH = 5;
//    private static int OBJECTS_IN_LEAF = 1;
//
//    private static int SPLIT_COST = 4;
//    private static int MAX_SPLITS_OF_VOXEL = 5;
//
//    private enum Plane {
//        XY,
//        XZ,
//        YZ,
//        NONE
//    }
//
//    private class Voxel {
//        double xMin;
//        double yMin;
//        double zMin;
//
//        double xMax;
//        double yMax;
//        double zMax;
//
//        public boolean pointIsIn(Vector3D p) {
//            return ((p.x > xMin) && (p.x < xMax) &&
//                    (p.y > yMin) && (p.y < yMax) &&
//                    (p.z > zMin) && (p.z < zMax));
//        }
//    }
//
//    private class KDNode {
//        Plane Plane;
//        Vector3D coord;
//
//        ArrayList<SceneObject> objects;
//
//        KDNode left;
//        KDNode right;
//    }
//
//    private KDNode root;
//    private Voxel boundingBox;
//
//    public void rebuild(ArrayList<SceneObject> objects) {
//
//    }
//
//    public void build(ArrayList<SceneObject> objects) {
//        boundingBox = new BoundingBox(objects);
//        root = recursiveBuild(objects, boundingBox, 0);
//    }
//
//    private KDNode recursiveBuild(ArrayList<SceneObject> objects,
//              Voxel v,
//              int iter) {
//
//        Plane p;
//        Vector3D c = new Vector3D(0, 0, 0);
//        p = findPlane(objects, v, iter, c);
//
//        if(p == Plane.NONE) {
//            return makeLeaf(objects);
//        }
//
//        Voxel vl = new Voxel();
//        Voxel vr = new Voxel();
//        splitVoxel(v, p, c, vl, vr);
//
//        int l_objects_count = filterOverlappedObjects(objects, vl);
//        KDNode l = rec_build(objects, vl, iter + 1);
//
//        int r_objects_count = filter_overlapped_objects(objects, vr);
//        KDNode r = rec_build(objects, vr, iter + 1);
//
//
//        KDNode node = new KDNode();
//        node.Plane = p;
//        node.coord = c;
//        node.left = l;
//        node.right = r;
//
//        return node;
//    }
//
//    private void splitVoxel(Voxel v, Plane p, Vector3D c, Voxel vl, Voxel vr) {
//        switch(p) {
//            case XY:
//                vl.zMax = c.z;
//                vr.zMin = c.z;
//                break;
//
//            case XZ:
//                vl.yMax = c.y;
//                vr.yMin = c.y;
//                break;
//
//            case YZ:
//                vl.xMax = c.x;
//                vr.xMin = c.x;
//                break;
//            case NONE:
//                // Unreachable case
//                throw new RuntimeException("unreachable state?");
//        }
//    }
//
//
//    private int filterOverlappedObjects(ArrayList<SceneObject> objects, Voxel vl) {
//        int i = 0;
//        int j = objects_count - 1;
//
//        Object3d * tmp;
//
//        // Put all objects, which overlap with voxel to the left part of array
//        while(i <= j) {
//            while((i < j) && (object_in_voxel(objects[i], v)))
//                i++;
//
//            while((j > i) && (!object_in_voxel(objects[j], v)))
//                j--;
//
//            tmp = objects[i];
//            objects[i] = objects[j];
//            objects[j] = tmp;
//            i++;
//            j--;
//        }
//
//        // Actually, after the loop, variable 'i' is a count of overlapped objects
//        return i;
//
//        return 0;
//    }
//
//    /*
//     * Using Surface Area Heuristic (SAH) for finding best split pane
//     *
//     * SAH = 0.5 * voxel_surface_area * number_of_objectsInVoxel
//     *
//     * splitted_SAH = splitCost
//     *                + 0.5 * left_voxel_surface_area * number_of_objects_in_left_voxel
//     *                + 0.5 * right_voxel_surface_area * number_of_objects_in_right_voxel
//     *
//     * Finding coordinate of split plane (XY, XZ or YZ) which minimizing SAH
//     *
//     * If can't find optimal split plane - returns NONE
//     *
//     * see: http://stackoverflow.com/a/4633332/653511
//     */
//    private Plane findPlane(ArrayList<SceneObject> objects, Voxel v, int tree_depth, Vector3D c) {
//
//        if((tree_depth >= MAX_TREE_DEPTH) || (objects.size() <= OBJECTS_IN_LEAF)) {
//            return Plane.NONE;
//        }
//
//        final double hx = v.xMax - v.xMin;
//        final double hy = v.yMax - v.yMin;
//        final double hz = v.zMax - v.zMin;
//
//        // Calculating square of each side of initial voxel
//        double Sxy = hx * hy;
//        double Sxz = hx * hz;
//        double Syz = hy * hz;
//
//        final double Ssum = Sxy + Sxz + Syz;
//
//        // Let's normalize square of each side of initial voxel
//        // to satisfy the following relationship:
//        // Sxy + Sxz + Syz = 1
//        Sxy /= Ssum;
//        Sxz /= Ssum;
//        Syz /= Ssum;
//
//        final double splitCost = SPLIT_COST;
//
//        // Assume that at the beginning best SAH has initial voxel
//        // SAH = 0.5 * square * objects_count
//        // square of initial voxel is Sxy + Sxz + Syz = 1
//        double bestSAH = objects.size();
//        // initial voxel doesn't have split pane
//        Plane p = Plane.NONE;
//
//        double currSAH;
//        Vector3D currSplitCoord = new Vector3D();
//        int i;
//        Voxel vl;
//        Voxel vr;
//        double l;
//        double r;
//
//        double sSplit;
//        double sNonSplit;
//
//        // Let's find split surface, which have the least SAH
//
//        // TODO: maybe do some refactoring (because of following 3 loops are very similar)
//
//        // trying to minimize SAH by splitting across XY plane
//        sSplit = Sxy;
//        sNonSplit = Sxz + Syz;
//        for(i = 1; i < MAX_SPLITS_OF_VOXEL; i++) {
//
//            l = ((float) i) / MAX_SPLITS_OF_VOXEL;
//            r = 1 - l;
//
//            // Current coordinate of split surface
//            currSplitCoord.z = v.zMin + l * hz;
//
//            splitVoxel(v, Plane.XY, currSplitCoord, vl, vr);
//
//            currSAH = (sSplit + l * sNonSplit) * objectsInVoxel(objects, vl)
//                    + (sSplit + r * sNonSplit) * objectsInVoxel(objects, vr)
//                    + splitCost;
//
//            if(currSAH < bestSAH) {
//                bestSAH = currSAH;
//                p = Plane.XY;
//                c = currSplitCoord;
//            }
//        }
//
//        // trying to minimize SAH by splitting across XZ plane
//        sSplit = Sxz;
//        sNonSplit = Sxy + Syz;
//        for(i = 1; i < MAX_SPLITS_OF_VOXEL; i++) {
//
//            l = ((float) i) / MAX_SPLITS_OF_VOXEL;
//            r = 1 - l;
//
//            // Current coordinate of split surface
//            currSplitCoord.y = v.yMin + l * hy;
//
//            splitVoxel(v, Plane.XZ, currSplitCoord, vl, vr);
//
//            currSAH = (sSplit + l * sNonSplit) * objectsInVoxel(objects, vl)
//                    + (sSplit + r * sNonSplit) * objectsInVoxel(objects, vr)
//                    + splitCost;
//
//            if(currSAH < bestSAH) {
//                bestSAH = currSAH;
//                p = Plane.XZ;
//                c = currSplitCoord;
//            }
//        }
//
//        // trying to minimize SAH by splitting across YZ plane
//        sSplit = Syz;
//        sNonSplit = Sxy + Sxz;
//        for(i = 1; i < MAX_SPLITS_OF_VOXEL; i++) {
//
//            l = ((float) i) / MAX_SPLITS_OF_VOXEL;
//            r = 1 - l;
//
//            // Current coordinate of split surface
//            currSplitCoord.x = v.xMin + l * hx;
//
//            splitVoxel(v, Plane.YZ, currSplitCoord, vl, vr);
//
//            currSAH = (sSplit + l * sNonSplit) * objectsInVoxel(objects, vl)
//                    + (sSplit + r * sNonSplit) * objectsInVoxel(objects, vr)
//                    + splitCost;
//
//            if(currSAH < bestSAH) {
//                bestSAH = currSAH;
//                p = Plane.YZ;
//                c = currSplitCoord;
//            }
//        }
//
//        return p;
//    }
//
//    private double objectsInVoxel(ArrayList<SceneObject> objects, Voxel v) {
//        int i;
//        int count = 0;
//        for(i = 0; i < objects.size(); i++)
//            if(v.objectInVoxel(objects.get(i))) {
//                ++count;
//            }
//
//        return count;
//    }
//
//    private KDNode makeLeaf(ArrayList<SceneObject> objects) {
//        return null;
//    }
//}
