package Logic;

import GUI.Grid.BasicGrid;
import GUI.Grid.MouseEventHandler;

import java.awt.*;

public class GridController {
    private Grid2D model;
    private BasicGrid view;

    public GridController(Grid2D model, BasicGrid view) {
        this.model = model;
        this.view = view;
    }

    public void init() {
        model.forEachCharacter((Integer x, Integer y, Character c) -> {
            view.addPiece(x, y, c.getSize(), c.isVertical());
            return null;
        });

        MouseEventHandler mouseHandler = new MouseEventHandler(this, view);
        view.addMouseListener(mouseHandler);
        view.addMouseMotionListener(mouseHandler);
    }

    public void changePiecePos(Point od, Point nw, Dimension dim) {
        // TODO: write method that gets the component from grid
    }

    public void changePiecePos(Component comp, Point oldPos, Point newPos) {
        Dimension objDim = BasicGrid.getRelativeSize(comp.getSize());
        if(model.move(oldPos.x, oldPos.y, newPos.x, newPos.y, objDim.width > objDim.height ? Rotation.HORIZONTAL : Rotation.VERTICAL)) {
            view.setPiecePos(comp, newPos);
        } else {
            view.setPiecePos(comp, oldPos);
        }
    }


}
