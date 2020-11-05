package com.smart.smartDB00.service.impl;

import com.smart.smartDB00.dao.b39.AddressMapper;
import com.smart.smartDB00.dao.b39.MachineMapper;
import com.smart.smartDB00.domain.Address;
import com.smart.smartDB00.service.MachineService;
import com.smart.smartDB00.domain.Machine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MachineServiceImpl implements MachineService {

    @Autowired
    private MachineMapper machineMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<Machine> selectMachine() {
        List<Machine> machineList = machineMapper.selectAll();
        List<Address> addressList = addressMapper.selectAll();
        for (Machine machine : machineList) {
            String detailedAddress = getDetailedAddress(machine.getAddressId(), addressList);
            machine.setDetailedAddress(detailedAddress);
        }
        return machineList;
    }

    private String getDetailedAddress(String code, List<Address> list) {
        String detailedAddress = "";
        for (Address node : list) {
            if (node.getId().equals(code)) {
                String pAddress = getDetailedAddress(node.getPid(), list);
                return ("".equals(pAddress) ? pAddress : pAddress + "/") + node.getName();
            }
        }
        return detailedAddress;
    }
}
