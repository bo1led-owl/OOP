package sys.pro;

import java.util.function.Supplier;

class AdjMatrixGraphTest extends GenericGraphTest<AdjMatrixGraph> {
    @Override
    public Supplier<AdjMatrixGraph> getSupplier() {
        return AdjMatrixGraph::new;
    }
}
