package darkchoco.taco;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="taco_order")
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date placed;
	
	@NotBlank(message="Name is required")
	@Column(name = "deliveryname")
	private String deliveryName;
	
	@NotBlank(message="Street is required")
	@Column(name = "deliverystreet")
	private String deliveryStreet;
	
	@NotBlank(message="City is required")
	@Column(name = "deliverycity")
	private String deliveryCity;
	
	@NotBlank(message="State is required")
	@Size(max = 2, message = "Only 2 Characters are allowed")
	@Column(name = "deliverystate")
	private String deliveryState;
	
	@NotBlank(message="Zip code is required")
	@Column(name = "deliveryzip")
	private String deliveryZip;
	
	@CreditCardNumber(message="Not a valid credit card number")
	@Column(name = "ccnumber")
	private String ccNumber;
	
	@Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
			message="Must be formatted MM/YY")
	@Column(name = "ccexpiration")
	private String ccExpiration;
	
	@Digits(integer=3, fraction=0, message="Invalid CVV")
	@Column(name = "cccvv")
	private String ccCVV;

	@ManyToMany(targetEntity = Taco.class)
	@JoinTable(name = "taco_order_tacos",
			joinColumns = @JoinColumn(name = "tacoorder"),
			inverseJoinColumns = @JoinColumn(name = "taco"))
	private List<Taco> tacos = new ArrayList<>();

	public void addTaco(Taco taco) {
		this.tacos.add(taco);
	}

	@PrePersist
	void placed() {
		this.placed = new Date();
	}
}
