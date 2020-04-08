/*******************************************************************************
 *
 *  MODULE PROJET 2 - 2015, 2016 (c) Daniel Le Berre - Université d'Artois - tous droits réservés.
 *   
 *  Le code source de ce programme est distributé pour raison pédagogique
 *  aux étudiants de troisième année de licence d'informatique de l'université d'Artois 2015.
 *  Ces étudiants ont le droit de l'exécuter, l'étudier, le modifier pour leur usage personnel.
 *  La redistribution de ce programme, sous quelle que forme que ce soit (source, binaire,
 *  listing, etc) est strictement interdite. 
 *  
 *******************************************************************************/
package math;

public class OrthonormalBasis {

    private final Vec3 u;
    private final Vec3 v;
    private final Vec3 w;

    public OrthonormalBasis(Point lookFrom, Point lookAt, Vec3 up) {
        // compute a correct coordinate system
        this.w = lookFrom.sub(lookAt).hat();
        this.u = up.cross(this.w).hat();
        this.v = this.w.cross(this.u).hat();
    }

    public Vec3 changeBase(Vec3 v) {
        return this.u.mul(v.x).add(this.v.mul(v.y)).add(this.w.mul(v.z)).hat();
    }
}
