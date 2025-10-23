package sys.pro;

import java.util.function.Supplier;

class IncidenceMatrixGraphTest extends GenericGraphTest<IncidenceMatrixGraph> {
    @Override
    public Supplier<IncidenceMatrixGraph> getSupplier() {
        return IncidenceMatrixGraph::new;
    }
}
