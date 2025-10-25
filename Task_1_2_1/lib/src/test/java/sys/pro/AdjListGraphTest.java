package sys.pro;

import java.util.function.Supplier;

class AdjListGraphTest extends GenericGraphTest<AdjListGraph> {
    @Override
    public Supplier<AdjListGraph> getSupplier() {
        return AdjListGraph::new;
    }
}
