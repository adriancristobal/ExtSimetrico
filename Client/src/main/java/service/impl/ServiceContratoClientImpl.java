package service.impl;

import com.google.gson.Gson;
import dao.DaoContratoClient;
import dao.DaoSicarioContratoClient;
import dao.impl.DaoContratoClientImpl;
import dao.impl.DaoSicarioContratoClientImpl;
import encrypt.Encryption;
import encrypt.impl.EncryptionAES;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Contrato;
import model.SicarioContrato;
import model.Usuario;
import modelClient.Detalle;
import org.w3c.dom.Document;
import service.ServiceContratoClient;
import service.jsonConverter.ConverterToJsonDetalleString;

import java.util.ArrayList;
import java.util.List;

public class ServiceContratoClientImpl implements ServiceContratoClient {

    DaoContratoClient dao;


    Gson gson = new Gson();

    @Inject
    public ServiceContratoClientImpl(DaoContratoClientImpl dao) {
        this.dao = dao;
    }

    @Override
    public Single<Either<String, List<Detalle>>> getAllContratos() {
        List<Detalle> detalleList = new ArrayList<>();
        Encryption encryption = new EncryptionAES();
        Single<Either<String, List<Contrato>>> result = dao.getAllContratos();
        if (result == null) {
            return Single.just(Either.left("No hay contratos"));
        } else {
            List<Contrato> contratoListdao = result.blockingGet().get();
            for (Contrato contrato : contratoListdao) {
                String detalleJson = encryption.decrypt(contrato.getDetalle(), contrato.getClave());
                Detalle detalle = gson.fromJson(detalleJson, Detalle.class);
                detalleList.add(detalle);
            }
        }
        return Single.just(Either.right(detalleList));
    }

    @Override
    public Single<Either<String, List<Detalle>>> getContratosByIdContratista(Integer idContratista) {
        List<Detalle> detalleList = new ArrayList<>();
        Encryption encryption = new EncryptionAES();
        Single<Either<String, List<Contrato>>> result = dao.getContratosByIdContratista(idContratista);
        if (result == null) {
            return Single.just(Either.left("No hay contratos"));
        } else {
            List<Contrato> contratoListdao = result.blockingGet().get();
            for (Contrato contrato : contratoListdao) {
                String detalleJson = encryption.decrypt(contrato.getDetalle(), contrato.getClave());
                Detalle detalle = gson.fromJson(detalleJson, Detalle.class);
                detalle.setIdContratista(contrato.getId_contratista());
                detalleList.add(detalle);
            }
        }
        return Single.just(Either.right(detalleList));
    }

    @Override
    public Single<Either<String, List<Detalle>>> getContratosByIdSicario(int idSicario) {
        List<Detalle> detalleList = new ArrayList<>();
        Encryption encryption = new EncryptionAES();
        Single<Either<String, List<Contrato>>> result = dao.getContratosByIdSicario(idSicario);
        if (result == null) {
            return Single.just(Either.left("No hay contratos"));
        } else {
            List<Contrato> contratoListdao = result.blockingGet().get();
            for (Contrato contrato : contratoListdao) {
                String detalleJson = encryption.decrypt(contrato.getDetalle(), contrato.getClave());
                Detalle detalle = gson.fromJson(detalleJson, Detalle.class);
                detalleList.add(detalle);
            }
        }
        return Single.just(Either.right(detalleList));
    }

    @Override
    public Single<Either<String, Contrato>> getContratoById(int id) {
        return dao.getContratoById(id);
    }

    @Override
    public Single<Either<String, Contrato>> postContrato(Contrato contrato) {
        String detalleJson = ConverterToJsonDetalleString.toJson(contrato.getDetalle());
        Encryption encryption = new EncryptionAES();
        String detalleEncrypt = encryption.encrypt(detalleJson, contrato.getClave());
        contrato.setDetalle(detalleEncrypt);
        return dao.postContrato(contrato);
    }

    @Override
    public Single<Either<String, Contrato>> putContrato(Contrato contrato) {
        return dao.putContrato(contrato);
    }

    @Override
    public Single<Either<String, String>> deleteContrato(int id) {
        return dao.deleteContrato(id);
    }

    @Override
    public Single<Either<String, List<Usuario>>> getSicariosByHabilityLevel(int habilityLevel) {
        return dao.getSicariosByHabilityLevel(habilityLevel);
    }

}
