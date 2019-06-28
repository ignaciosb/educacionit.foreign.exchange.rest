

package com.educacionit.forex.rest.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.educacionit.forex.rest.entity.CurrencyEntity;
import com.educacionit.forex.rest.repository.ICurrencyRepository;


@RequestMapping ("/currencies")
@RestController
public class ForexController {


    @Autowired
    private ICurrencyRepository dao;
  

    @RequestMapping (method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll () {


        List<CurrencyEntity> list = this.dao.findAll ();

        if (list == null || list.isEmpty ()) {

            return ResponseEntity.status (HttpStatus.NOT_FOUND).body ("{\"message\" : \"No hay cotizaciones !!!\"}");

        } else {

            return ResponseEntity.ok (list);
        }
    }

    @RequestMapping (value="status/{status}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getByStatus (@PathVariable("status") Boolean status) {


        List<CurrencyEntity> list = this.dao.findAllByEnabled (status);

        if (list == null || list.isEmpty ()) {

            return ResponseEntity.status (HttpStatus.NOT_FOUND).body ("{\"message\" : \"No hay cotizaciones !!!\"}");

        } else {

            return ResponseEntity.ok (list);
        }
    }

    @RequestMapping (value="{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getById (@PathVariable("id") Long id) {


        CurrencyEntity item = this.dao.findById (id).orElse (null);

        if (item == null ) {

            return ResponseEntity.status (HttpStatus.NOT_FOUND).body ("{\"message\" : \"La moneda no existe !!!\"}");

        } else {

            return ResponseEntity.ok (item);
        }
    }

    @RequestMapping (value="last/active", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> get () {


        CurrencyEntity item = this.dao.findTop1ByEnabledOrderByIdDesc (true);

        if (item == null ) {

            return ResponseEntity.status (HttpStatus.NOT_FOUND).body ("{\"message\" : \"La moneda no existe !!!\"}");

        } else {

            return ResponseEntity.ok (item);
        }
    }

    @RequestMapping (method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create (@RequestBody @Validated CurrencyEntity item) {


        this.dao.save (item);
        return ResponseEntity.noContent ().build ();
    }

    @RequestMapping (value="{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
                                                               produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update (@RequestBody @Validated CurrencyEntity item, @PathVariable ("id") Long id) {


        CurrencyEntity c = this.dao.findById (id).orElse (null);

        if (c == null) {

            return ResponseEntity.status (HttpStatus.NOT_FOUND).body ("{\"message\" : \"La moneda " + id + " no existe !!!\"}");
        }


        c.setName (item.getName ());
        c.setDescription (item.getDescription ());
        c.setValue (item.getValue ());

        this.dao.save (item);

        return ResponseEntity.ok (c);
    }

    @RequestMapping (value="{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteById (@PathVariable("id") Long id) {


        CurrencyEntity item = this.dao.findById (id).orElse (null);

        if (item == null ) {

            return ResponseEntity.status (HttpStatus.NOT_FOUND).body ("{\"message\" : \"La moneda no existe !!!\"}");

        } else {

            this.dao.deleteById (id);
            return ResponseEntity.noContent ().build();
        }
    }
}