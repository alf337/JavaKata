package alf.exercises.leetcode;

import java.util.*;

/**
 * You have a number of envelopes with widths and heights given as a pair of integers (w, h). One envelope can fit into another if and only if both the width and height of one envelope is greater than the width and height of the other envelope.
 *
 * What is the maximum number of envelopes can you Russian doll? (put one inside other)
 *
 * Note:
 * Rotation is not allowed.
 *
 * Example:
 *
 * Input: [[5,4],[6,4],[6,7],[2,3]]
 * Output: 3
 * Explanation: The maximum number of envelopes you can Russian doll is 3 ([2,3] => [5,4] => [6,7]).
 */
public class RussianDollEnvelopes {

    public int maxEnvelopes(int[][] envelopes) {

        if (envelopes == null || envelopes.length == 0) return 0;

        List<Env> envList = buildEnvList(envelopes);
        if (envList.isEmpty()) return 0;

        Map<Env, Node> nodeMap = buildNodeMap(envList);
        if (nodeMap.size() == 1) return 1;

        return calculateMaxDepth(nodeMap);
    }

    private int calculateMaxDepth(Map<Env, Node> nodeMap) {
        int max = 1;
        int count = 0;

        for (Node node : nodeMap.values()) {
            count++;
            System.out.println("Node Loop ="+ node);
            System.out.println("max=" + max + " node=" + node.env + " count=" + count);

            //left loop
            for (Env leftEnv : node.left) {
                int depth = 1 + leftDepth(leftEnv, nodeMap);
                if (depth > max) max = depth;
            }

            //right loop
            for (Env rightEnv : node.right) {
                int depth = 1 + rightDepth(rightEnv, nodeMap);
                if (depth > max) max = depth;
            }
        }

        return max;
    }

    private int leftDepth(Env e, Map<Env, Node> nodeMap) {

        Node leftNode = nodeMap.get(e);
        System.out.println("Left Node=" + leftNode.env);
        if (leftNode.left.isEmpty()) {
            return 1;
        } else {
            int max = 0;
            for (Env leftEnv : leftNode.left) {
                int depth = 1 + leftDepth(leftEnv, nodeMap);
                if (depth > max) max = depth;
            }
            return max;
        }
    }

    private int rightDepth(Env e, Map<Env, Node> nodeMap) {

        Node rightNode = nodeMap.get(e);
        System.out.println("Right Node=" + rightNode.env);
        if (rightNode.right.isEmpty()) {
            return 1;
        } else {
            int max = 0;
            for (Env rightEnv : rightNode.right) {
                int depth = 1 + rightDepth(rightEnv, nodeMap);
                if (depth > max) max = depth;
            }
            return max;
        }
    }

    private List<Env> buildEnvList(int[][] envelopes) {
        List<Env> envList = new ArrayList<>();

        for (int i = 0; i < envelopes.length; i++) {
            if (envelopes[i].length == 2) {
                envList.add(Env.of(envelopes[i][0], envelopes[i][1]));
            }
        }
        return envList;
    }

    private Map<Env, Node> buildNodeMap(List<Env> envList) {
        Map<Env, Node> nodeMap = new HashMap<>();

        for (Env env : envList) {
            Node node = Node.of(env);
            nodeMap.put(env, node);
        }

        for (Node node: nodeMap.values()) {
            for (Node other : nodeMap.values()) {
                if (!node.env.equals(other.env)) {
                    // exclude self

                    if (node.env.isSmaller(other.env)) {
                        node.right.add(other.env);
                    }

                    if (node.env.isLarger(other.env)) {
                        node.left.add(other.env);
                    }
                }
            }
        }

        // print
        for (Node node : nodeMap.values()) {

            System.out.println(node.env + " l=" + node.left.size() + " r=" + node.right.size());
            System.out.println(node);
            System.out.println();
        }
        System.out.println("Node count=" + nodeMap.size());

        //verify
//        System.out.println("Verify begin ------\n");
//        for (Node node : nodeMap.values()) {
//            for (Env leftEnv: node.left) {
//                if (leftEnv.height >= node.env.height) {
//                    System.out.println("error left height: " + node.env + leftEnv);
//                }
//                if (leftEnv.width >= node.env.width) {
//                    System.out.println("error left width: " + node.env + leftEnv);
//                }
//            }
//
//            for (Env rightEnv: node.right) {
//                if (rightEnv.height <= node.env.height) {
//                    System.out.println("error right height" + node.env + rightEnv);
//                }
//                if (rightEnv.width <= node.env.width) {
//                    System.out.println("error right width" + node.env + rightEnv);
//                }
//            }
//        }
//        System.out.println("Verify complete ------\n");

        System.out.println("-------------------------------\n");
        return nodeMap;
    }

    public static class Env {
        int height;
        int width;

        private Env(int h, int w) {
            this.height = h;
            this.width = w;
        }

        public static Env of(int h, int w) {
            return new Env(h, w);
        }

        public boolean isSmaller(Env other) {
            return this.height < other.height && this.width < other.width;
        }

        public boolean isLarger(Env other) {
            return this.height > other.height && this.width > other.width;
        }

       @Override
        public String toString() {
            return "(" + height + "," + width + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Env other = (Env) o;
            return height == other.height &&
                    width == other.width;
        }

        @Override
        public int hashCode() {
            return Objects.hash(height, width);
        }
    }

    public static class Node {
        Env env;
        List<Env> left;
        List<Env> right;

        private Node(Env env) {
            this.env = env;
            this.left = new ArrayList<>();
            this.right = new ArrayList<>();
        }

        public static Node of(Env env) {
            return new Node(env);
        }

        @Override
        public String toString() {
            return "{e=" + env +
                    " l=" + left +
                    " r=" + right +
                    '}';
        }
    }
}
