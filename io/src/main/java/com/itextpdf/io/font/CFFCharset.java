package com.itextpdf.io.font;

import java.util.HashMap;
import java.util.Map;

public abstract class CFFCharset {
    private final boolean isCIDFont;
    public final Map<Integer, Integer> sidOrCidToGid = new HashMap(250);
    public final Map<Integer, Integer> gidToSid = new HashMap(250);
    public final Map<String, Integer> nameToSid = new HashMap(250);
    public final Map<Integer, Integer> gidToCid = new HashMap();
    public final Map<Integer, String> gidToName = new HashMap(250);

    CFFCharset(boolean isCIDFont) {
        this.isCIDFont = isCIDFont;
    }

    public boolean isCIDFont() {
        return this.isCIDFont;
    }

    public void addSID(int gid, int sid, String name) {
        if (this.isCIDFont) {
            throw new IllegalStateException("Not a Type 1-equivalent font");
        } else {
            this.sidOrCidToGid.put(sid, gid);
            this.gidToSid.put(gid, sid);
            this.nameToSid.put(name, sid);
            this.gidToName.put(gid, name);
        }
    }

    public void addCID(int gid, int cid) {
        if (!this.isCIDFont) {
            throw new IllegalStateException("Not a CIDFont");
        } else {
            this.sidOrCidToGid.put(cid, gid);
            this.gidToCid.put(gid, cid);
        }
    }

    int getSIDForGID(int sid) {
        if (this.isCIDFont) {
            throw new IllegalStateException("Not a Type 1-equivalent font");
        } else {
            Integer gid = (Integer)this.gidToSid.get(sid);
            return gid == null ? 0 : gid;
        }
    }

    int getGIDForSID(int sid) {
        if (this.isCIDFont) {
            throw new IllegalStateException("Not a Type 1-equivalent font");
        } else {
            Integer gid = (Integer)this.sidOrCidToGid.get(sid);
            return gid == null ? 0 : gid;
        }
    }

    public int getGIDForCID(int cid) {
        if (!this.isCIDFont) {
            throw new IllegalStateException("Not a CIDFont");
        } else {
            Integer gid = (Integer)this.sidOrCidToGid.get(cid);
            return gid == null ? 0 : gid;
        }
    }

    int getSID(String name) {
        if (this.isCIDFont) {
            throw new IllegalStateException("Not a Type 1-equivalent font");
        } else {
            Integer sid = (Integer)this.nameToSid.get(name);
            return sid == null ? 0 : sid;
        }
    }

    public String getNameForGID(int gid) {
        if (this.isCIDFont) {
            throw new IllegalStateException("Not a Type 1-equivalent font");
        } else {
            return (String)this.gidToName.get(gid);
        }
    }

    public int getCIDForGID(int gid) {
        if (!this.isCIDFont) {
            throw new IllegalStateException("Not a CIDFont");
        } else {
            Integer cid = (Integer)this.gidToCid.get(gid);
            return cid != null ? cid : 0;
        }
    }
}
