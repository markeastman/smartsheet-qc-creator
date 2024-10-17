package org.example;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.util.ArrayList;
import java.util.List;

public class HtmlStringSelection implements Transferable {

    private static final List<DataFlavor> htmlFlavors = new ArrayList<>(2);

    static {
        htmlFlavors.add(DataFlavor.allHtmlFlavor);
    }

    private String html;

    public HtmlStringSelection(String html) {
        this.html = html;
    }

    public DataFlavor[] getTransferDataFlavors() {
        return (DataFlavor[]) htmlFlavors.toArray(new DataFlavor[htmlFlavors.size()]);
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return htmlFlavors.contains(flavor);
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {

        if (flavor == DataFlavor.allHtmlFlavor)
            return html;
        else
            throw new UnsupportedFlavorException(flavor);
    }
}
