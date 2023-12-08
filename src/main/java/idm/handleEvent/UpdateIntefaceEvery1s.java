package idm.handleEvent;

import java.util.concurrent.atomic.AtomicBoolean;

import idm.models.DownloadInfo;
import javafx.scene.control.TextArea;

public class UpdateIntefaceEvery1s implements Runnable {
    private DownloadInfo downloadInfo;
    private long downloadedBefore1s;
    private volatile AtomicBoolean pause = new AtomicBoolean(false);
    private volatile AtomicBoolean cancel = new AtomicBoolean(false);



    public UpdateIntefaceEvery1s(DownloadInfo downloadInfo, long downloadedBefore1s, AtomicBoolean pause, AtomicBoolean cancel) {
     this.downloadInfo = downloadInfo;
     this.downloadedBefore1s = downloadedBefore1s;
     this.pause = pause;
     this.cancel = cancel;
     
    }
    

    @Override
    public void run() {
        if (cancel.get()) {
            return;
        }

        if (!pause.get()) {
            long downloadedSize = downloadInfo.getDownloaded();
            long size = downloadInfo.getSize();
            double transferRate;
            double timeleft;

            //bytes - bytes
            transferRate = (downloadedSize - downloadedBefore1s) / 1000;
            System.out.println("TransferRate: " + transferRate + "KB/s");
            timeleft = (size - downloadedSize) / (transferRate * 1024);
            System.out.println("Timeleft: " + timeleft + "s");
            downloadInfo.setTransferRate(transferRate);
            downloadInfo.setTimeleft(timeleft);
            this.downloadedBefore1s = downloadedSize;
            System.out.println(downloadInfo.formatFileSize(this.downloadedBefore1s));
        }
        else {
        }
    }

}