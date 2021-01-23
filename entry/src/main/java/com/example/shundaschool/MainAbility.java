package com.example.shundaschool;

import ohos.ace.ability.AceAbility;
import ohos.aafwk.content.Intent;

public class MainAbility extends AceAbility {
    @Override
    public void onStart(Intent intent) {
        LocationAbility.register(this);
        super.onStart(intent);
    }

    @Override
    public void onStop() {
        LocationAbility.deregister();
        super.onStop();
    }
}
