package vn.unigap.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.unigap.api.dto.in.PageDtoIn;
import vn.unigap.api.dto.in.UpdateUserDtoIn;
import vn.unigap.api.dto.in.UserDtoIn;
import vn.unigap.common.controller.AbstractResponseController;
import vn.unigap.api.dto.out.PageDtoOut;
import vn.unigap.api.dto.out.UserDtoOut;
import vn.unigap.api.service.UserService;

import java.util.HashMap;

@RestController
@RequestMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "User", description = "Quản lý user")
@SecurityRequirement(name = "Authorization")
public class UserController extends AbstractResponseController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Lấy danh sách users",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponsePageUser.class
                                    )
                            )
                    )
            }
    )
    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
//    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<?> list(@Valid PageDtoIn pageDtoIn) {
        return responseEntity(() -> {
            return this.userService.list(pageDtoIn);
        });
    }

    @Operation(
            summary = "Lấy thông tin user theo id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseUser.class
                                    )
                            )}
                    )
            }
    )
    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> get(@PathVariable(value = "id") Long id) {
        return responseEntity(() -> {
            return this.userService.get(id);
        });
    }


    @Operation(
            summary = "Thêm mới user",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseUser.class
                                    )
                            )}
                    )
            }
    )
    @PostMapping(value = "")
    public ResponseEntity<?> create(@RequestBody @Valid UserDtoIn userDtoIn) {
        return responseEntity(() -> {
            return this.userService.create(userDtoIn);
        }, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Cập nhật user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseUser.class
                                    )
                            )}
                    )
            }
    )
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody @Valid UpdateUserDtoIn updateUserDtoIn) {
        return responseEntity(() -> {
            return this.userService.update(id, updateUserDtoIn);
        });
    }

    @Operation(
            summary = "Xóa user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = vn.unigap.common.response.ApiResponse.class
                                    )
                            )}
                    )
            }
    )
    @DeleteMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        return responseEntity(() -> {
            this.userService.delete(id);
            return new HashMap<>();
        });
    }

    private static class ResponseUser extends vn.unigap.common.response.ApiResponse<UserDtoOut> {
    }

    private static class ResponsePageUser extends vn.unigap.common.response.ApiResponse<PageDtoOut<UserDtoOut>> {
    }
}
