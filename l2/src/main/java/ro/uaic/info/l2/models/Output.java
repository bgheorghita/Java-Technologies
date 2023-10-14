package ro.uaic.info.l2.models;

public class Output {
    private final int order;
    private final long size;
    private final int numComponents;
    private final int minDegree;
    private final int maxDegree;
    private final double avgDegree;
    private final double diameter;
    private final double radius;

    private Output(int order, long size, int numComponents, int minDegree, int maxDegree, double avgDegree, double diameter, double radius) {
        this.order = order;
        this.size = size;
        this.numComponents = numComponents;
        this.minDegree = minDegree;
        this.maxDegree = maxDegree;
        this.avgDegree = avgDegree;
        this.diameter = diameter;
        this.radius = radius;
    }

    public int getOrder() {
        return order;
    }

    public long getSize() {
        return size;
    }

    public int getNumComponents() {
        return numComponents;
    }

    public int getMinDegree() {
        return minDegree;
    }

    public int getMaxDegree() {
        return maxDegree;
    }

    public double getAvgDegree() {
        return avgDegree;
    }

    public double getDiameter() {
        return diameter;
    }

    public double getRadius() {
        return radius;
    }

    public static OutputBuilder builder() {
        return new OutputBuilder();
    }

    public static class OutputBuilder {
        private int order;
        private long size;
        private int numComponents;
        private int minDegree;
        private int maxDegree;
        private double avgDegree;
        private double diameter;
        private double radius;

        public OutputBuilder withOrder(int order) {
            this.order = order;
            return this;
        }

        public OutputBuilder withSize(long size) {
            this.size = size;
            return this;
        }

        public OutputBuilder withNumComponents(int numComponents) {
            this.numComponents = numComponents;
            return this;
        }

        public OutputBuilder withMinDegree(int minDegree) {
            this.minDegree = minDegree;
            return this;
        }

        public OutputBuilder withMaxDegree(int maxDegree) {
            this.maxDegree = maxDegree;
            return this;
        }

        public OutputBuilder withAvgDegree(double avgDegree) {
            this.avgDegree = avgDegree;
            return this;
        }

        public OutputBuilder withDiameter(double diameter) {
            this.diameter = diameter;
            return this;
        }

        public OutputBuilder withRadius(double radius) {
            this.radius = radius;
            return this;
        }

        public Output build() {
            return new Output(order, size, numComponents, minDegree, maxDegree, avgDegree, diameter, radius);
        }
    }
}
