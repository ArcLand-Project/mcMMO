package com.gmail.nossr50.core.data.blockmeta.conversion;

import com.gmail.nossr50.core.config.ChunkConversionOptions;
import com.gmail.nossr50.mcMMO;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;

public class BlockStoreConversionXDirectory implements Runnable {
    BukkitScheduler scheduler;
    File dataDir;
    File[] zDirs;
    BlockStoreConversionZDirectory[] converters;
    private int taskID, i;
    private org.bukkit.World world;

    public BlockStoreConversionXDirectory() {
        this.taskID = -1;
    }

    public void start(org.bukkit.World world, File dataDir) {
        this.world = world;
        this.scheduler = mcMMO.p.getServer().getScheduler();
        this.converters = new BlockStoreConversionZDirectory[ChunkConversionOptions.getInstance().getConversionRate()];
        this.dataDir = dataDir;

        if (this.taskID >= 0) {
            return;
        }

        this.taskID = this.scheduler.runTaskLater(mcMMO.p, this, 1).getTaskId();
        return;
    }

    @Override
    public void run() {
        if (!this.dataDir.exists()) {
            stop();
            return;
        }

        if (!this.dataDir.isDirectory()) {
            this.dataDir.delete();
            stop();
            return;
        }

        if (this.dataDir.listFiles().length <= 0) {
            this.dataDir.delete();
            stop();
            return;
        }

        this.zDirs = this.dataDir.listFiles();

        for (this.i = 0; (this.i < ChunkConversionOptions.getInstance().getConversionRate()) && (this.i < this.zDirs.length); this.i++) {
            if (this.converters[this.i] == null) {
                this.converters[this.i] = new BlockStoreConversionZDirectory();
            }

            this.converters[this.i].start(this.world, this.dataDir, this.zDirs[this.i]);
        }

        stop();
    }

    public void stop() {
        if (this.taskID < 0) {
            return;
        }

        this.scheduler.cancelTask(this.taskID);
        this.taskID = -1;

        this.dataDir = null;
        this.zDirs = null;
        this.world = null;
        this.scheduler = null;
        this.converters = null;
    }
}
