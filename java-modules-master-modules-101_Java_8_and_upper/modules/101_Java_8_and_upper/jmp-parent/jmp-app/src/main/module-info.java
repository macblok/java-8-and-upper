module jmp.app {
    requires jmp.cloud.bank.impl;
    requires jmp.cloud.service.impl;
    requires jmp.dto;

    exports com.epam.jmpapp;
}