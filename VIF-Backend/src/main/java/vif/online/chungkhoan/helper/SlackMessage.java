package vif.online.chungkhoan.helper;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder(builderClassName = "Builder")
@Getter
@Setter
public class SlackMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	private String username;
	private String text;
	private String icon_emoji;
}
